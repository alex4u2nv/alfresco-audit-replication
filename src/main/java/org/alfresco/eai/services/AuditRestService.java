package org.alfresco.eai.services;

import org.alfresco.eai.model.AuditResponse;
import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.MediaType;

@Component
public class AuditRestService {
  private static final Logger LOG = LoggerFactory.getLogger(AuditRestService.class);


  @Value("${alfresco.url}")
  private String alfrescoUrl;
  @Value("${alfresco.audit.query.path}")
  private String auditPath;

  @Value("${alfresco.audit.application}")
  private String auditApplication;

  @Autowired
  List<?> jsonProviders;

  @Autowired
  AlfrescoAuthentication authentication;

  /**
   * Fetch Audit stream from/to sequence ids
   * @param from
   * @param to
   * @return
   */
  public AuditResponse fetchAudit(long from, long to) {
    LOG.debug("Fetching with \n\tURL: {}\n\tPath: {}\n\tFrom: {}\n\tTo: {}\n\t"
        , alfrescoUrl
        , auditPath
        , from
        , to);

    WebClient webClient = WebClient.create(alfrescoUrl, jsonProviders)
                                   .path(auditPath, auditApplication)
                                   .query("fromId", from)
                                   .query("verbose", true)
                                   .query("alf_ticket", authentication.getAlfrescoTicket())
                                   .accept(MediaType.APPLICATION_JSON);
    if (to > 0) {
      webClient.query("toId", to);
    }
    AuditResponse response = null;
    try {
      response=webClient.get(AuditResponse.class);
    } catch (NotAuthorizedException nae) {
      authentication.clearTicket(); //clear auth and retry.
      webClient.query("alf_ticket", authentication.getAlfrescoTicket());
      response = webClient.get(AuditResponse.class);
    }
    LOG.debug("Response entries found: " + response.getCount());
    return response;
  }

  /**
   * Fetch Audit from audit sequence id
   * @param from
   * @return
   */
  public AuditResponse fetchAudit(long from) {
    return fetchAudit(from,-1);
  }
}

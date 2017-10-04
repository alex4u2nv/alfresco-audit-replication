package org.alfresco.eai.services;

import org.alfresco.eai.model.TicketModel;
import org.apache.cxf.jaxrs.client.WebClient;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

@Component
public class AlfrescoAuthentication {
  private static final Logger LOG = LoggerFactory.getLogger(AlfrescoAuthentication.class);
  private String ALF_TICKET;

  @Value("${alfresco.url}")
  private String alfrescoUrl;
  @Value("${alfresco.username}")
  private String username;
  @Value("${alfresco.password}")
  private String password;
  @Autowired
  List<?> webClientProviders;
  private static final String LOGIN_SERVICE = "/s/api/login";
  private static final String LOGIN_JSON = "'{' \"username\": \"{0}\", \"password\": \"{1}\" '}'";

  public String getAlfrescoTicket() {
    if (ALF_TICKET != null)
      return ALF_TICKET;

    TicketModel data = WebClient.create(alfrescoUrl, webClientProviders).path(LOGIN_SERVICE)
                                .accept(MediaType.APPLICATION_JSON)
                                .post(MessageFormat.format(LOGIN_JSON, username, password),
                                      TicketModel.class);

    //data should be null, lines above should fail before we get null.
    ALF_TICKET = data.getData().get("ticket");
    LOG.debug("alf_ticket: {}", ALF_TICKET);

    return ALF_TICKET;
  }

  public void clearTicket() {
    ALF_TICKET = null;
  }

}

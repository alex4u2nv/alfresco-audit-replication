package org.alfresco.eai.routes;

import org.alfresco.eai.services.AuditRestService;
import org.alfresco.eai.services.AuditSyncService;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.elasticsearch5.ElasticsearchConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.elasticsearch.index.IndexNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuditExportRoute extends RouteBuilder {
  private static final Logger LOG = LoggerFactory.getLogger(AuditExportRoute.class);

  @Value("${route.audit.export.enabled}")
  private boolean routeEnabled;


  @Autowired
  AuditRestService auditRestService;

  @Override
  public void configure()
      throws Exception {
    if (!routeEnabled) {
      LOG.info("Audit Export not enabled");
      return;
    }

    from("scheduler://pollAuditStream?backoffMultiplier={{alfresco.audit.backoffMultiplier" +
             "}}&backoffIdleThreshold={{alfresco.audit.backoffTreshold}}&delay={{alfresco.audit" +
             ".timer" +
             ".period}}")
        .bean("configurations", "auditSearchRequest")
        .doTry()
        .to("elasticsearch5://docker-cluster?operation=SEARCH&transportAddresses={{elasticsearch" +
                ".transportAddress}}&clientTransportSniff=false")
        .doCatch(IndexNotFoundException.class)
        .log("Index Not yet initialized")
        .doFinally()
        .bean("auditSyncService", "getStartId")
        .to("direct:fetchAudit");


    from("direct:fetchAudit")
        .setHeader("from", header(AuditSyncService.FROM_INDEX))
        .setHeader("to", header(AuditSyncService.TO_INDEX))
        .setHeader(ElasticsearchConstants.PARAM_INDEX_NAME, simple("auditsample"))
        .setHeader(ElasticsearchConstants.PARAM_INDEX_TYPE, simple("Audit"))
        .bean("auditRestService", "fetchAudit(${header.from}, ${header.to})")
        .choice()
        .when(simple("${body.count} > 0"))
        .to("direct:updateElasticSearch")
        .otherwise()
        .setProperty(Exchange.SCHEDULER_POLLED_MESSAGES, simple("false"));



    from("direct:updateElasticSearch")
        .split(simple("${body.entries}"))
        .setHeader(ElasticsearchConstants.PARAM_INDEX_ID, simple("${body.id}"))
        .marshal().json(JsonLibrary.Gson)
        .to("elasticsearch5://docker-cluster?operation=INDEX&transportAddresses={{elasticsearch.transportAddress}}" +
                "&clientTransportSniff=false");

  }

}

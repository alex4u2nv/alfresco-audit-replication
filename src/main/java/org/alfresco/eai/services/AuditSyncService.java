package org.alfresco.eai.services;

import org.apache.camel.Exchange;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuditSyncService {
  public static final String FROM_INDEX = "FROM_INDEX";
  public static final String TO_INDEX = "TO_INDEX";

  @Value("${alfresco.audit.query.batchSize}")
  int batchSize;

  public void getStartId(Exchange exchange) {
    final SearchResponse response = exchange.getIn().getBody(SearchResponse.class);
    int startIndex = 0;
    final SearchHits hits = response!=null? response.getHits():null;

    if (hits != null && hits.getHits() != null && hits.getHits().length > 0)
      startIndex = Integer.parseInt(hits.getHits()[0].getId())+1;
    exchange.getOut().setHeader(FROM_INDEX, startIndex);
    exchange.getOut().setHeader(TO_INDEX, startIndex+batchSize);
  }

  public int getToId(Exchange exchange) {
    int from = exchange.getIn().getHeader(FROM_INDEX, Integer.class);
    return from + batchSize;
  }
}

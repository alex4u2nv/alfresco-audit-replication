package org.alfresco.eai.configs;

import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import org.alfresco.eai.model.TicketModel;
import org.apache.camel.component.cxf.spring.SpringJAXRSClientFactoryBean;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class Configurations {

 @Value("${alfresco.url}")
 private String alfrescoUrl;

 @Value("${elasticsearch.audit.index}")
 private String elAuditIndex;

 @Value("${elasticsearch.audit.type}")
 private String elAuditType;

 @Bean
 public JacksonJsonProvider jsonProvider() {
  return new JacksonJsonProvider();
 }

 @Bean
 public List<?> webClientProviders() {
  return new ArrayList<>(Arrays.asList(jsonProvider()));
 }


 @Bean
 public MappingJsonFactory mappingJsonFactory() {
  return new MappingJsonFactory();
 }


 @Bean
 public ObjectMapper objectMapper() {
  return new ObjectMapper();
 }


 @Bean
 public SearchRequest auditSearchRequest() {
  SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
  searchSourceBuilder.size(1);
  searchSourceBuilder.sort("id", SortOrder.DESC);
  SearchRequest request = new SearchRequest();
  request.source(searchSourceBuilder);
  request.indices(elAuditIndex);
  return request;
 }





}

# Audit Replication to ES 5.6

#### Main Configurations found in `src/main/resources/application.properties`
```properties
# Elastic Search IP Address, and Binding port (not http 9200)
elasticsearch.transportAddress=192.168.99.101:9300
# Alfresco Repository Host or IP
alfresco.host=192.168.99.101
# Protocol for Alfresco content services platform
alfresco.protocol=http
# Port that the repository is hosted on
alfresco.port=8443
# Alfresco's platform context
alfresco.context=alfresco
# Alfresco's username and password for an account capable of reading audit stream
alfresco.username=admin
alfresco.password=admin
```

#### Usage
##### Run within maven project
```bash
mvn spring-boot:run
```
##### package and deploy
```bash
mvn package
java -jar audit-x.y.z.jar
```
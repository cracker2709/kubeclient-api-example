# kubeclient-api-example
A RestFul service interacting with Kubernetes APIS

Use of https://quarkus.io/guides/kubernetes-client

- Requirements
JDK 11
Maven 3+
An available kubernetes cluster
Authentication done with proxy

  `$ kubectl proxy --port=8080 &`
  
or with custom service account with appropriate cluster role and cluster
role bindings

- Launch Project

  `$ mvn spring-boot:run` 

- Methods callable within Swagger-ui
http://localhost:9142/swagger-ui.html


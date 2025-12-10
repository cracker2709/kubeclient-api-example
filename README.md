# kubeclient-api-example
### A RestFul service interacting with Kubernetes APIS

- Use of https://quarkus.io/guides/kubernetes-client

### Requirements
JDK 21
Maven 3+
An available kubernetes cluster

Locally you can use kind
Installation instructions https://kind.sigs.k8s.io/docs/user/quick-start/

```shell
$ kind create cluster --config=kind/kind-config.yaml
```
or use authentication through proxy
```shell
$ kubectl proxy --port=8080 &
```  
or with custom service account with appropriate cluster role and cluster
role bindings

- Launch Project

  `$ mvn spring-boot:run` 

- Methods callable within Swagger-ui
http://localhost:8080/swagger-ui/index.html


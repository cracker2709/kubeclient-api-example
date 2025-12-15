# kubeclient-api-example
### A RestFul service interacting with Kubernetes APIS

- Use of https://quarkus.io/guides/kubernetes-client

### Requirements
JDK 21
Maven 3+
An available kubernetes cluster

Locally you can use kind
Installation instructions https://kind.sigs.k8s.io/docs/user/quick-start/

```bash
$ kind create cluster --config=kind/kind-config.yaml
```
or use authentication through proxy
```bash
$ kubectl proxy --port=8080 &
```  
or with custom service account with appropriate cluster role and cluster
role bindings

- Launch Project

```bash
$ mvn spring-boot:run
```

When launching locally, check the use of local cluster config.
```logs
   __ __         __                                __
  / //_/ __ __  / /  ___       ___  __ __   ___   / / ___   ____ ___   ____
 / ,<   / // / / _ \/ -_)     / -_) \ \ /  / _ \ / / / _ \ / __// -_) / __/
/_/|_|  \_,_/ /_.__/\__/      \__/ /_\_\  / .__//_/  \___//_/   \__/ /_/
                                         /_/
 :: Spring Boot (v3.2.0) ::

[16:51:27.858] [INFO] Starting KubeApplication using Java 21.0.9 with PID 239140 (/data/github/Perso/kubeclient-api-example/target/classes started by gap in /data/github/Perso/kubeclient-api-example) 
[16:51:27.860] [INFO] The following 1 profile is active: "swagger" 
[16:51:30.053] [INFO] Tomcat initialized with port 8080 (http) 
[16:51:30.064] [INFO] Initializing ProtocolHandler ["http-nio-8080"] 
[16:51:30.067] [INFO] Starting service [Tomcat] 
[16:51:30.067] [INFO] Starting Servlet engine: [Apache Tomcat/10.1.16] 
[16:51:30.143] [INFO] Initializing Spring embedded WebApplicationContext 
[16:51:30.145] [INFO] Root WebApplicationContext: initialization completed in 2180 ms 
[16:51:30.551] [INFO] Using local kubeconfig 
[16:51:30.973] [WARN] 
```

- Methods callable within Swagger-ui
http://localhost:8080/swagger-ui/index.html


### Kind deployment

To build image locally
```bash
docker build -t kubeclient-api .
```

To deploy the application into the kind cluster with appropriate service account and roles bindings

```bash
helm install kubeclient-api ./kind/resources/kubeapi/ [--set image.tag=<version>]
```

When application is launched, you should see in logs use of service account

```logs
   __ __         __                                __
  / //_/ __ __  / /  ___       ___  __ __   ___   / / ___   ____ ___   ____
 / ,<   / // / / _ \/ -_)     / -_) \ \ /  / _ \ / / / _ \ / __// -_) / __/
/_/|_|  \_,_/ /_.__/\__/      \__/ /_\_\  / .__//_/  \___//_/   \__/ /_/
                                         /_/
 :: Spring Boot (v3.2.0) ::

[15:55:58.236] [INFO] Starting KubeApplication v1.3.1 using Java 21.0.9 with PID 1 (/app.jar started by root in /) 
[15:55:58.244] [INFO] The following 1 profile is active: "swagger" 
[15:56:02.224] [INFO] Tomcat initialized with port 8080 (http) 
[15:56:02.248] [INFO] Initializing ProtocolHandler ["http-nio-8080"] 
[15:56:02.254] [INFO] Starting service [Tomcat] 
[15:56:02.255] [INFO] Starting Servlet engine: [Apache Tomcat/10.1.16] 
[15:56:02.377] [INFO] Initializing Spring embedded WebApplicationContext 
[15:56:02.380] [INFO] Root WebApplicationContext: initialization completed in 3876 ms 
[15:56:02.850] [INFO] Using in-cluster service account 
[15:56:03.826] [WARN] 
```

This will deploy the application with a NodePort service type. You can access the application using the Ip of the node.
To get IP of the node

```bash
kubectl get nodes kind-control-plane -o wide | grep -v INT | awk '{print $6}'
```

Then use 31000 port to access the application
http://<NODE_IP>:31000/swagger-ui/index.html

To uninstall the application

```bash
helm uninstall kubeclient-api
```

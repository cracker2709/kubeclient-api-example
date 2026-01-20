# kubeclient-api-example
### A RestFul service interacting with Kubernetes APIS

- Use of https://quarkus.io/guides/kubernetes-client

### Requirements
- JDK 21
- Maven 3+

Or 
- Python 3.13+

And an available kubernetes cluster

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


### Java API

#### Build Project

```bash
- Launch Project with Java / maven

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


#### Kind deployment

To build image locally
```bash
docker build -f Dockerfile-java -t <DOCKER_REPOSITORY>/kubeclient-routers-java:<TAG> .
```

NB: Make sure to tag the image appropriately if you are using a different version than latest and set up appropriate docker registry if needed.

To deploy the application into the kind cluster with appropriate service account and roles bindings

```bash
helm install kubeclient-api-java ./kind/resources/kubeapi/ --create-namespace --namespace kubeclient # --set image.tag=<version> to set specific image tag
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
Or follow helm output to get the node IP

```bash
1. Get the application URL by running these commands:
  export NODE_PORT=$(kubectl get --namespace kubeclient -o jsonpath="{.spec.ports[0].nodePort}" services kubeclient-routers-kubeapi)
  export NODE_IP=$(kubectl get nodes --namespace kubeclient -o jsonpath="{.items[0].status.addresses[0].address}")
  echo http://$NODE_IP:$NODE_PORT
```
Then use 30000 port to access the application
http://<NODE_IP>:30000/swagger-ui/index.html

To uninstall the application

```bash
helm uninstall kubeclient-api-java
```


### Python API

#### Setup Virtualenv

```bash
$ python3.13 -m venv venv
$ source venv/bin/activate
$ pip install -r kube-api-pyp/requirements.txt
```

#### Launch Application with fastAPI

```bash
fastapi run kube-api-pyp/app/main.py
```

When launching locally, check the use of local cluster config.
```logs
   FastAPI   Starting production server 🚀
 
             Searching for package file structure from directories with __init__.py files
2026-01-21 10:31:15,409 [MainProcess: 40817] [MainThread: 138768533875584] [INFO] root: API is starting up
             Importing from /data/github/Perso/kubeclient-api-example
 
    module   📁 kube-api-pyp       
             ├── 🐍 __init__.py    
             └── 📁 app            
                 ├── 🐍 __init__.py
                 └── 🐍 main.py    
 
      code   Importing the FastAPI app object from the module with the following code:
 
             from kube-api-pyp.app.main import app
 
       app   Using import string: kube-api-pyp.app.main:app
 
    server   Server started at http://0.0.0.0:8000
    server   Documentation at http://0.0.0.0:8000/docs
 
             Logs:
 
      INFO   Started server process [40817]
2026-01-21 10:31:15,430 [MainProcess: 40817] [MainThread: 138768533875584] [INFO] uvicorn.error: Started server process [40817]
      INFO   Waiting for application startup.
2026-01-21 10:31:15,430 [MainProcess: 40817] [MainThread: 138768533875584] [INFO] uvicorn.error: Waiting for application startup.
      INFO   Application startup complete.
2026-01-21 10:31:15,431 [MainProcess: 40817] [MainThread: 138768533875584] [INFO] uvicorn.error: Application startup complete.
      INFO   Uvicorn running on http://0.0.0.0:8000 (Press CTRL+C to quit)
2026-01-21 10:31:15,432 [MainProcess: 40817] [MainThread: 138768533875584] [INFO] uvicorn.error: Uvicorn running on http://0.0.0.0:8000 (Press CTRL+C to quit)
2026-01-21 10:36:27,394 [MainProcess: 40817] [AnyIO worker thread: 138768401462976] [WARNING] kube-api-pyp.app.routers.kube: In-cluster kube config load failed: Service host/port is not set.; trying fallback
2026-01-21 10:36:27,400 [MainProcess: 40817] [AnyIO worker thread: 138768401462976] [INFO] kube-api-pyp.app.routers.kube: Loaded local kubeconfig
```

- Methods callable within Swagger-ui
http://localhost:8000/docs

#### Kind deployment
To build image locally
```bash
docker build -f Dockerfile-pyp -t <DOCKER_REPOSITORY>/kubeclient-routers-pyp:<TAG> .
```

NB: Make sure to tag the image appropriately if you are using a different version than latest and set up appropriate docker registry if needed.

To deploy the application into the kind cluster (kubeclient-api-java should be already deployed with appropriate service account and roles bindings to reuse them otherwise it won't work)

```bash
helm install kubeclient-api-pyp ./kind/resources/kubeapi-pyp/ --create-namespace --namespace kubeclient # --set image.tag=<version> to set specific image tag
```

When application is launched, you should see in logs use of service account

```logs
   FastAPI   Starting production server 🚀
 
             Searching for package file structure from directories with         
             __init__.py files                                                  
2026-01-21 09:14:16,856 [MainProcess: 1] [MainThread: 124260313865152] [INFO] root: API is starting up
             Importing from /code
 
    module   📁 app            
             ├── 🐍 __init__.py
             └── 🐍 main.py    
 
      code   Importing the FastAPI app object from the module with the following
             code:                                                              
 
             from app.main import app
 
       app   Using import string: app.main:app
 
    server   Server started at http://0.0.0.0:80
    server   Documentation at http://0.0.0.0:80/docs
 
             Logs:
 
      INFO   Started server process [1]
2026-01-21 09:14:16,879 [MainProcess: 1] [MainThread: 124260313865152] [INFO] uvicorn.error: Started server process [1]
2026-01-21 09:14:16,879 [MainProcess: 1] [MainThread: 124260313865152] [INFO] uvicorn.error: Waiting for application startup.
      INFO   Waiting for application startup.
      INFO   Application startup complete.
2026-01-21 09:14:16,880 [MainProcess: 1] [MainThread: 124260313865152] [INFO] uvicorn.error: Application startup complete.
      INFO   Uvicorn running on http://0.0.0.0:80 (Press CTRL+C to quit)
2026-01-21 09:14:16,880 [MainProcess: 1] [MainThread: 124260313865152] [INFO] uvicorn.error: Uvicorn running on http://0.0.0.0:80 (Press CTRL+C to quit)
2026-01-21 09:14:17,788 [MainProcess: 1] [MainThread: 124260313865152] [INFO] root: Health check endpoint called
      INFO   10.244.2.1:49446 - "GET /api/v1/health HTTP/1.1" 200
2026-01-21 09:14:25,109 [MainProcess: 1] [MainThread: 124260313865152] [INFO] root: Health check endpoint called
      INFO   10.244.2.1:36728 - "GET /api/v1/health HTTP/1.1" 200
2026-01-21 09:14:25,218 [MainProcess: 1] [AnyIO worker thread: 124260204025536] [INFO] app.routers.kube: Loaded in-cluster kubeconfig
```   

This will deploy the application with a NodePort service type. You can access the application using the Ip of the node.
To get IP of the node
```bash
kubectl get nodes kind-control-plane -o wide | grep -v INT | awk '{print $6}'
```
Or follow helm output to get the node IP    
```bash
1. Get the application URL by running these commands:
    export NODE_PORT=$(kubectl get --namespace kubeclient -o jsonpath="{.spec.ports[0].nodePort}" services kubeclient-routers-kubeapi-pyp)
    export NODE_IP=$(kubectl get nodes --namespace kubeclient -o jsonpath="{.items[0].status.addresses[0].address}")
    echo http://$NODE_IP:$NODE_PORT
```
Then use 30001 port to access the application
http://<NODE_IP>:30001/docs

To uninstall the application

```bash
helm uninstall kubeclient-api-pyp
```

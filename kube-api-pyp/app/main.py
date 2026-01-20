import sys

from fastapi import FastAPI

from .routers import kube
import logging
app = FastAPI(dependencies=[])


# Configurer le logger racine pour que tous les modules héritent du handler
root_log = logging.getLogger()
root_log.setLevel(logging.DEBUG)

stream_handler = logging.StreamHandler(sys.stdout)
log_formatter = logging.Formatter(
    "%(asctime)s [%(processName)s: %(process)d] [%(threadName)s: %(thread)d] [%(levelname)s] %(name)s: %(message)s"
)
stream_handler.setFormatter(log_formatter)
root_log.addHandler(stream_handler)
root_log.info("API is starting up")

app.include_router(kube.router)

@app.get("/")
async def root():
    root_log.info("Root endpoint called")
    return {"message": "Welcome to kubenertes Python API, built with fastAPI !"}

@app.get("/api/v1/health")
async def health_check():
    root_log.info("Health check endpoint called")
    return {"status": "OK"}

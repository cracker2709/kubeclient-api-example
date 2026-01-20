import logging

from fastapi import APIRouter
from kubernetes import client, config

from ..models.objects import PodInfos, ErrorResponse

router = APIRouter()
log = logging.getLogger()


@router.get("/api/v1/podInfos/{namespace_name}")
def list_pods(namespace_name: str):
    try:
        load_kube_config()
    except Exception as e:
        log.error(f"Failed to load kube config: {e}")
        error = ErrorResponse(detail="Failed to load kube config")
        return error
    v1 = client.CoreV1Api()
    result = list()
    for pod in v1.list_namespaced_pod(namespace=namespace_name).items:
        name = pod.metadata.name
        labels = pod.metadata.labels or {}
        image = labels.get("app.kubernetes.io/name", "")
        version = labels.get("app.kubeclient.version", "")
        log.info(f'{name} | {image} | {version}')
        podinfo = PodInfos(name=name, image=image, version=version)
        result.append(podinfo)
    return result


def load_kube_config():

    logger = logging.getLogger(__name__)

    def try_incluster():
        config.load_incluster_config()
        logger.info("Loaded in-cluster kubeconfig")

    def try_kubeconfig():
        config.load_kube_config()
        logger.info("Loaded local kubeconfig")

    try:
        try_incluster()
    except Exception as primary_exc:
        logger.warning(f"In-cluster kube config load failed: {primary_exc}; trying fallback")
        try:
            try_kubeconfig()
        except Exception as e:
            logger.error(f"Fallback kube config load also failed: {e}")
            raise primary_exc from e

if __name__ == "__main__":
    # Test the load_kube_config function
    try:
        load_kube_config()
        print("Kube config loaded successfully.")
    except Exception as e:
        print(f"Failed to load kube config: {e}")
package kubernetes.services;


import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import kubernetes.beans.NameSpaceItem;
import kubernetes.beans.PodItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j

public class KubeService {

    private Config config;
    private KubernetesClient kubernetesClient;

    private static final String INDEX = "index";

    private static final String ACTIVE_MQ = "activemq";

    private static final String KUBERNETES_IMAGE_NAME = "app.kubernetes.io/name";

    private static final String DOCKERTAG = "dockertag";

    @PostConstruct
    public void init() {
        // init code goes here
        config = Config.autoConfigure(null);
        if (isInCluster()) {
            log.info("Using in-cluster service account");
        } else if (isLocalKubeconfig()) {
            log.info("Using local kubeconfig");
        } else {
            log.info("Using environment/system properties for Kubernetes config");
        }
        kubernetesClient = new DefaultKubernetesClient(config);
    }


    /**
     * List All details of namespaces
     *
     * @return
     */
    public List<Namespace> getNameSpaces() {
        List<Namespace> allNS = this.kubernetesClient.namespaces().list().getItems();
        return allNS;
    }

    /**
     * List all pods details for a given namespace
     *
     * @param namespace
     * @return
     */
    public List<Pod> getPodsDetailsForNameSpace(String namespace) {
        return this.kubernetesClient.pods().inNamespace(namespace).list().getItems();
    }


    /**
     * Get Infos of pods in specified namespace
     *
     * @param namespace
     * @return
     */
    public NameSpaceItem getNameSpaceItem(String namespace) {
        // remove index & activemq named pods
        List<Pod> pods = getPodsDetailsForNameSpace(namespace).stream().filter(x -> (!x.getMetadata().getName().startsWith(INDEX) && !x.getMetadata().getName().contains(ACTIVE_MQ))).collect(Collectors.toList());
        List<PodItem> podItems = new ArrayList<>();
        for (Pod pod : pods) {
            podItems.add(PodItem.builder().name(pod.getMetadata().getName()).image(pod.getMetadata().getLabels().get(KUBERNETES_IMAGE_NAME)).version(pod.getMetadata().getLabels().get(DOCKERTAG)).build());
        }
        return NameSpaceItem.builder().name(namespace).podItems(podItems).build();
    }

    private boolean isInCluster() {
        return System.getenv("KUBERNETES_SERVICE_HOST") != null
                && Files.exists(Paths.get("/var/run/secrets/kubernetes.io/serviceaccount/token"));
    }

    private boolean isLocalKubeconfig() {
        String kubeconfigEnv = System.getenv("KUBECONFIG");
        Path kubeconfigPath = kubeconfigEnv != null
                ? Paths.get(kubeconfigEnv)
                : Paths.get(System.getProperty("user.home"), ".kube", "config");
        return Files.exists(kubeconfigPath);
    }



}

package kubernetes.services;


import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import kubernetes.beans.NameSpaceItem;
import kubernetes.beans.PodItem;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class KubeService {

    @Value("${quarkus.kubernetes-client.master}")
    private String master;

    private Config config;
    private KubernetesClient kubernetesClient;

    @PostConstruct
    public void init(){
        // init code goes here
        config = new ConfigBuilder().withMasterUrl(master).build();
        kubernetesClient = new DefaultKubernetesClient(config);
    }


    /**
     * List All details of namespaces
     * @return
     */
    public List<Namespace> getNameSpaces() {
        List<Namespace> allNS = this.kubernetesClient.namespaces().list().getItems();
        return allNS;
    }

    /**
     * List all pods details for a given namespace
     * @param namespace
     * @return
     */
    public List<Pod> getPodsDetailsForNameSpace(String namespace) {
        return this.kubernetesClient.pods().inNamespace(namespace).list().getItems();
    }


    /**
     * Get Infos of pods in specified namespace
     * @param namespace
     * @return
     */
    public NameSpaceItem getNameSpaceItem(String namespace) {
        // remove index & activemq named pods
        List<Pod> pods = getPodsDetailsForNameSpace(namespace).stream().filter(x -> (!x.getMetadata().getName().startsWith("index") && !x.getMetadata().getName().contains("activemq"))).collect(Collectors.toList());        List<PodItem> podItems = new ArrayList<>();
        for (Pod pod : pods) {
            podItems.add(PodItem.builder().name(pod.getMetadata().getName()).image(pod.getMetadata().getLabels().get("app.kubernetes.io/name")).version(pod.getMetadata().getLabels().get("dockertag")).build());
        }
        return NameSpaceItem.builder().name(namespace).podItems(podItems).build();
    }



}

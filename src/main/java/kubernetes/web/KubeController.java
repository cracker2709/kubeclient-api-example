package kubernetes.web;


import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.Pod;
import kubernetes.beans.NameSpaceItem;
import kubernetes.services.KubeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/kubeinfos")
public class KubeController {

    @Autowired
    private KubeService kubeService;


    @GetMapping(value = "/namespaces/all")
    public List<Namespace> getAllNameSpaces() {
        log.info("Retrieve all namespaces");
        return kubeService.getNameSpaces();
    }

    @GetMapping(value = "/podsDetails/{namespace}")
    public List<Pod> getPodsDetailsForNameSpace(@PathVariable("namespace") String namespace) {
        return kubeService.getPodsDetailsForNameSpace(namespace);
    }


    @GetMapping(value = "/podsInfos/{namespace}")
    public NameSpaceItem getPodInfosForNameSpace(@PathVariable("namespace") String namespace) {
        return kubeService.getNameSpaceItem(namespace);
    }








}
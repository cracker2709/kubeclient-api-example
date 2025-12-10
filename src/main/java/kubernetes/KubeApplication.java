package kubernetes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"io.kubernetes.client.apis", "kubernetes.client", "kubernetes.config", "kubernetes.services", "kubernetes.web"})
@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class KubeApplication {

    public static void main(String[] args) {
        SpringApplication.run(KubeApplication.class, args);
    }
}

package kubernetes.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config/application.yml")
@ComponentScan(basePackages = {"io.kubernetes.client.apis", "kubernetes.client", "kubernetes.config", "kubernetes.services", "kubernetes.web"})
public class KubeConfigurationProperties {

}

package kubernetes;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@Log4j2
@SpringBootApplication
@ComponentScan(basePackages = { "io.kubernetes.client.apis", "kubernetes.client", "kubernetes.config", "kubernetes.services", "kubernetes.web"})
@EnableAutoConfiguration(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class KubeApplication {

	public static void main(String[] args) {
		SpringApplication.run(KubeApplication.class, args);
	}
}

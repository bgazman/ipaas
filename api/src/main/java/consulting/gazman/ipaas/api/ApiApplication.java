package consulting.gazman.ipaas.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// ApiApplication.java
@SpringBootApplication
@EnableDiscoveryClient  // Make sure to import: org.springframework.cloud.client.discovery.EnableDiscoveryClient
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
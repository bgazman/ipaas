package consulting.gazman.ipaas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@EnableWebSecurity
public class IpaasSecurity {
    public static void main(String[] args) {
        SpringApplication.run(IpaasSecurity.class, args);
    }
}

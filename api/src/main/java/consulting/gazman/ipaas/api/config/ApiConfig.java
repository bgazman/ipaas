package consulting.gazman.ipaas.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "com.yourcompany.ipaas.security",  // Include security module configuration
    "com.yourcompany.ipaas.api"
})
public class ApiConfig {
    // API-specific beans and configuration
}
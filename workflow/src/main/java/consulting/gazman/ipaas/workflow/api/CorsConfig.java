package consulting.gazman.ipaas.workflow.api;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*") // Your frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .exposedHeaders("Content-Type");
    }
}
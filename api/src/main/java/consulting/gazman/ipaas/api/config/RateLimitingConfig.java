package consulting.gazman.ipaas.api.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.common.util.concurrent.RateLimiter;
import java.util.Optional;

import reactor.core.publisher.Mono;


@Configuration
public class RateLimitingConfig {
    @Bean
    public RateLimiter rateLimiter() {
        return RateLimiter.create(10.0); // 10 requests per second
    }
    
    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.just(
            Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("X-API-Key"))
                .orElse("anonymous")
        );
    }
}

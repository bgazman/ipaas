package consulting.gazman.ipaas.api.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import com.google.common.util.concurrent.RateLimiter;

import java.time.Duration;

@Configuration
public class ApiGatewayConfig {
    
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("example-service", r -> r
                .path("/api/v1/service/**")
                .filters(f -> f
                    .rewritePath("/api/v1/service/(?<segment>.*)", "/${segment}")
                    .circuitBreaker(c -> c
                        .setName("defaultCircuitBreaker")
                        .setFallbackUri("forward:/fallback"))
                    .filter(rateLimiterFilter())
                    .retry(retryConfig -> retryConfig
                        .setRetries(3)
                        .setStatuses(HttpStatus.INTERNAL_SERVER_ERROR)))
                .uri("http://target-service"))
            .build();
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
            .circuitBreakerConfig(CircuitBreakerConfig.custom()
                .slidingWindowSize(10)
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(10))
                .permittedNumberOfCallsInHalfOpenState(5)
                .build())
            .timeLimiterConfig(TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(4))
                .build())
            .build());
    }

    // Simple in-memory rate limiter using Guava
    @Bean
    public RequestRateLimiterGatewayFilterFactory.Config rateLimiterConfig() {
        return new RequestRateLimiterGatewayFilterFactory.Config()
            .setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
    }

    @Bean
    public org.springframework.cloud.gateway.filter.GatewayFilter rateLimiterFilter() {
        RateLimiter rateLimiter = RateLimiter.create(10.0); // 10 requests per second
        return (exchange, chain) -> {
            if (!rateLimiter.tryAcquire()) {
                exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                return exchange.getResponse().setComplete();
            }
            return chain.filter(exchange);
        };
    }
}
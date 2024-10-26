package consulting.gazman.ipaas.api.filter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ApiKeyAuthFilter extends AbstractGatewayFilterFactory<ApiKeyAuthFilter.Config> {
    
    public ApiKeyAuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String apiKey = request.getHeaders().getFirst("X-API-Key");
            
            if (!isValidApiKey(apiKey)) {
                return handleUnauthorized(exchange);
            }
            
            return chain.filter(exchange);
        };
    }
    
    public static class Config {
        // Configuration properties
    }

    private boolean isValidApiKey(String apiKey) {
        // Implement your API key validation logic
        return apiKey != null && !apiKey.isEmpty();
    }

    private Mono<Void> handleUnauthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
}
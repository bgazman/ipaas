package consulting.gazman.ipaas.common.logging;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        try {
            LoggingContext.initializeRequest();
            
            String correlationId = request.getHeader("X-Correlation-ID");
            if (correlationId != null) {
                LoggingContext.put(LoggingContext.CORRELATION_ID, correlationId);
            }
            
            filterChain.doFilter(request, response);
        } finally {
            LoggingContext.clear();
        }
    }
}
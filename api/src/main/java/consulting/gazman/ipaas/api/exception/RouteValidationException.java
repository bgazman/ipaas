package consulting.gazman.ipaas.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RouteValidationException extends RuntimeException {
    
    public RouteValidationException(String message) {
        super(message);
    }
    
    public RouteValidationException(List<String> errors) {
        super(String.format("Route validation failed: %s", String.join(", ", errors)));
    }
}
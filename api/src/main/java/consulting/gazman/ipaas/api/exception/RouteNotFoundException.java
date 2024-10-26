package consulting.gazman.ipaas.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RouteNotFoundException extends RuntimeException {
    
    public RouteNotFoundException(String id) {
        super(String.format("Route not found with id: %s", id));
    }
}
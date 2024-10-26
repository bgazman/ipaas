package consulting.gazman.ipaas.api.validator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import consulting.gazman.ipaas.api.exception.RouteValidationException;
import consulting.gazman.ipaas.api.model.RouteDefinition;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RouteValidator {
    public void validateRoute(RouteDefinition route) {
        List<String> errors = new ArrayList<>();
        
        if (StringUtils.isEmpty(route.getPath())) {
            errors.add("Path is required");
        }
        
        if (StringUtils.isEmpty(route.getTargetUrl())) {
            errors.add("Target URL is required");
        }
        
        if (!errors.isEmpty()) {
            throw new RouteValidationException(errors);
        }
        
        // Validate URL format
        try {
            new URL(route.getTargetUrl());
        } catch (MalformedURLException e) {
            throw new RouteValidationException("Invalid target URL format");
        }
    }
}

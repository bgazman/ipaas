package consulting.gazman.ipaas.api.service;

import org.springframework.stereotype.Service;

import consulting.gazman.ipaas.api.exception.RouteNotFoundException;
import consulting.gazman.ipaas.api.model.RouteDefinition;
import consulting.gazman.ipaas.api.repository.RouteRepository;
import consulting.gazman.ipaas.api.validator.RouteValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
    private final RouteValidator routeValidator;
    
    public RouteDefinition createRoute(RouteDefinition route) {
        routeValidator.validateRoute(route);
        log.info("Creating new route: {}", route.getName());
        return routeRepository.save(route);
    }
    
    public RouteDefinition updateRoute(String id, RouteDefinition route) {
        routeValidator.validateRoute(route);
        log.info("Updating route: {}", id);
        return routeRepository.findById(id)
            .map(existingRoute -> routeRepository.save(route))
            .orElseThrow(() -> new RouteNotFoundException(id));
    }
    
    public void deleteRoute(String id) {
        log.info("Deleting route: {}", id);
        routeRepository.deleteById(id);
    }
    
    public List<RouteDefinition> getAllRoutes() {
        return routeRepository.findAll();
    }
    
    public RouteDefinition getRoute(String id) {
        return routeRepository.findById(id)
            .orElseThrow(() -> new RouteNotFoundException(id));
    }
}
package consulting.gazman.ipaas.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import consulting.gazman.ipaas.api.model.RouteDefinition;
import consulting.gazman.ipaas.api.service.RouteService;

import org.springframework.security.access.prepost.PreAuthorize;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/routes")
@RequiredArgsConstructor
public class RouteManagementController {
    private final RouteService routeService;
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RouteDefinition> createRoute(@Valid @RequestBody RouteDefinition route) {
        RouteDefinition createdRoute = routeService.createRoute(route);
        return ResponseEntity.ok(createdRoute);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RouteDefinition> updateRoute(
            @PathVariable String id,
            @Valid @RequestBody RouteDefinition route) {
        RouteDefinition updatedRoute = routeService.updateRoute(id, route);
        return ResponseEntity.ok(updatedRoute);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRoute(@PathVariable String id) {
        routeService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping
    public ResponseEntity<List<RouteDefinition>> getAllRoutes() {
        List<RouteDefinition> routes = routeService.getAllRoutes();
        return ResponseEntity.ok(routes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RouteDefinition> getRoute(@PathVariable String id) {
        RouteDefinition route = routeService.getRoute(id);
        return ResponseEntity.ok(route);
    }
}
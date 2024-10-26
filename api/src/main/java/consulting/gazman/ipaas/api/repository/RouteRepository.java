package consulting.gazman.ipaas.api.repository;

// src/main/java/com/yourcompany/ipaas/api/repository/RouteRepository.java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import consulting.gazman.ipaas.api.model.RouteDefinition;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<RouteDefinition, String> {
    // Find by name
    Optional<RouteDefinition> findByName(String name);
    
    // Find all active routes
    List<RouteDefinition> findByIsActiveTrue();
    
    // Find by path pattern
    Optional<RouteDefinition> findByPath(String path);
    
    // Find routes by target URL
    List<RouteDefinition> findByTargetUrlContaining(String targetUrl);
    
    // Custom query to find routes by method
    @Query("SELECT r FROM RouteDefinition r JOIN r.methods m WHERE m = :method")
    List<RouteDefinition> findByHttpMethod(String method);
    
    // Check if path exists
    boolean existsByPath(String path);
    
    // Find routes with specific timeout
    List<RouteDefinition> findByTimeoutLessThan(Integer timeout);
}


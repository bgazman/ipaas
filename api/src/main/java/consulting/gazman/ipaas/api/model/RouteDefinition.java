package consulting.gazman.ipaas.api.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

// src/main/java/com/yourcompany/ipaas/api/model/RouteDefinition.java
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

@Entity
@Table(name = "routes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String path;
    
    @Column(nullable = false)
    private String targetUrl;
    
    private boolean isActive;
    
    @ElementCollection
    @CollectionTable(name = "route_methods", joinColumns = @JoinColumn(name = "route_id"))
    @Column(name = "method")
    private List<String> methods;
    
    @ElementCollection
    @CollectionTable(name = "route_headers", joinColumns = @JoinColumn(name = "route_id"))
    @MapKeyColumn(name = "header_key")
    @Column(name = "header_value")
    private Map<String, String> headers;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "route_id")
    private List<FilterDefinition> filters;
    
    private Integer timeout;
    
    private Integer retryCount;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}


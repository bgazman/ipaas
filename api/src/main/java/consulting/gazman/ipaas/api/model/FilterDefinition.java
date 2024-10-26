package consulting.gazman.ipaas.api.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Entity
@Table(name = "route_filters")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private String name;
    
    @ElementCollection
    @CollectionTable(name = "filter_parameters", 
                    joinColumns = @JoinColumn(name = "filter_id"))
    @MapKeyColumn(name = "param_key")
    @Column(name = "param_value")
    private Map<String, String> parameters;
    
    private int order;

    // If you need to override equals/hashCode due to the Map field
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilterDefinition that = (FilterDefinition) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
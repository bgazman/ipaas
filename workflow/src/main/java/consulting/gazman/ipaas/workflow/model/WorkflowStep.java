package consulting.gazman.ipaas.workflow.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "workflow_steps", schema = "workflow")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "workflow")
public class WorkflowStep {
    @Id
    private UUID id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "workflow_id")
    private Workflow workflow;
    
    private String stepName;
    private Integer stepOrder;
    private String status;
    private String type;
    private Integer retryCount;
    private Integer maxRetries;
    private LocalDateTime slaDeadline;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private String errorMessage;
    private String parameters;

    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL)
    private List<StepStatusHistory> statusHistory;
}
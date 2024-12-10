package consulting.gazman.ipaas.workflow.entity;

import java.time.LocalDateTime;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "workflows", schema = "workflow")
public class Workflow {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "correlation_id")
    private String correlationId;

    @Column(nullable = false)
    private String status;

    private String type;

    @Column(name = "max_retries", nullable = false)
    private int maxRetries;

    @Column(name = "retry_count", nullable = false)
    private int retryCount;

    @Column(name = "sla_deadline")
    private LocalDateTime slaDeadline;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    private int priority;

    @Column(name = "parent_workflow_id")
    private UUID parentWorkflowId;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void getSteps() {

    }

    // Getters and setters omitted for brevity

}
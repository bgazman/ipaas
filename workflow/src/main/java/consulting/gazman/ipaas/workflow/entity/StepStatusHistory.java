package consulting.gazman.ipaas.workflow.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;


@Entity
@Table(name = "step_status_history", schema = "workflow")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StepStatusHistory {
    @Id
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "step_id")
    private WorkflowStep step;
    
    private String oldStatus;
    private String newStatus;
    private String reason;
    private LocalDateTime changedAt;
    private String changedBy;
}
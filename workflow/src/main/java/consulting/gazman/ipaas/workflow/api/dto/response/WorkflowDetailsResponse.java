package consulting.gazman.ipaas.workflow.api.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

public class WorkflowDetailsResponse {
    private UUID id;
    private String name;
    private String type;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
    private Object payload;  // The actual workflow payload as JSON object
    private List<WorkflowStepDetail> steps;

    public WorkflowDetailsResponse() {}

    public WorkflowDetailsResponse(UUID id, String name, String type, String status, LocalDateTime createdAt,
                                   LocalDateTime updatedAt, LocalDateTime completedAt, Object payload, List<WorkflowStepDetail> steps) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.completedAt = completedAt;
        this.payload = payload;
        this.steps = steps;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public List<WorkflowStepDetail> getSteps() {
        return steps;
    }

    public void setSteps(List<WorkflowStepDetail> steps) {
        this.steps = steps;
    }


}
package consulting.gazman.ipaas.workflow.api.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
public class WorkflowResubmitResponse {
    private UUID workflowId;

    private String name;
    private String status;
    private LocalDateTime resubmittedAt;
    public WorkflowResubmitResponse(UUID workflowId, String name, String status, LocalDateTime resubmittedAt) {
        this.workflowId = workflowId;
        this.name = name;
        this.status = status;
        this.resubmittedAt = resubmittedAt;
    }
    public UUID getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(UUID workflowId) {
        this.workflowId = workflowId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDateTime getResubmittedAt() {
        return resubmittedAt;
    }
    public void setResubmittedAt(LocalDateTime resubmittedAt) {
        this.resubmittedAt = resubmittedAt;
    }

    // Constructor, getters, and setters
}
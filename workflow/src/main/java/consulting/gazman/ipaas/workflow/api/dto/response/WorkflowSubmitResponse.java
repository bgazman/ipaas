package consulting.gazman.ipaas.workflow.api.dto.response;

import java.util.UUID;

public class WorkflowSubmitResponse {
    private UUID workflowId;
    public UUID getWorkflowId() {
        return workflowId;
    }
    public void setWorkflowId(UUID workflowId) {
        this.workflowId = workflowId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    private String status;
    public WorkflowSubmitResponse(UUID workflowId, String status) {
        this.workflowId = workflowId;
        this.status = status;
    }
}
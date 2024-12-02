package consulting.gazman.ipaas.workflow.api.dto.request;

import java.util.UUID;

public class WorkflowResubmitRequest {
    private UUID workflowId;

    public WorkflowResubmitRequest(UUID workflowId) {
        this.workflowId = workflowId;
    }

    public UUID getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(UUID workflowId) {
        this.workflowId = workflowId;
    }


    // Getter and setter
}
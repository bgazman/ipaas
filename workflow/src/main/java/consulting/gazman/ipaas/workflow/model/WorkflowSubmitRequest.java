package consulting.gazman.ipaas.workflow.model;

public class WorkflowSubmitRequest {
    private String workflowName;
    private String payload;

    // Getters and Setters
    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}

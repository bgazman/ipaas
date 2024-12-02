package consulting.gazman.ipaas.workflow.model;

import java.util.UUID;


public class WorkflowSubmitResponse {

    private UUID workflowId;
    private WorkflowError workflowError;  // Nullable field for error
    private String status;
    private String message;

    // Constructor for success
    public WorkflowSubmitResponse(UUID workflowId, String status, String message) {
        this.workflowId = workflowId;
        this.status = status;
        this.message = message;
        this.workflowError = null;  // No error in success case
    }

    // Constructor for failure
    public WorkflowSubmitResponse(WorkflowError workflowError, String status, String message) {
        this.workflowId = null;  // No workflowId in failure case
        this.status = status;
        this.message = message;
        this.workflowError = workflowError;  // Set the error in failure case
    }

    // Getters and Setters
    public UUID getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(UUID workflowId) {
        this.workflowId = workflowId;
    }

    public WorkflowError getError() {
        return workflowError;
    }

    public void setError(WorkflowError workflowError) {
        this.workflowError = workflowError;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

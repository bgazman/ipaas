package consulting.gazman.ipaas.workflow.api.dto.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import consulting.gazman.ipaas.workflow.api.exception.ApiException;
import com.fasterxml.jackson.core.JsonProcessingException;

public class WorkflowSubmitRequest {
    private String workflowName;
    private String workflowType;
    private Object payload;

    // Default constructor
    public WorkflowSubmitRequest() {}

    // Getters and setters
    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public String getWorkflowType() {
        return workflowType;
    }

    public void setWorkflowType(String workflowType) {
        this.workflowType = workflowType;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public String getPayloadAsString() {
        try {
            return new ObjectMapper().writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new ApiException("Failed to serialize payload", e);
        }
    }
}
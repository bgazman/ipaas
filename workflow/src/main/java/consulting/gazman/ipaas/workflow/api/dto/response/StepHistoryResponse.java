package consulting.gazman.ipaas.workflow.api.dto.response;

import java.util.UUID;
import java.util.List;

public class StepHistoryResponse {
    private UUID stepId;
    private String stepName;
    private List<StepStatusHistoryEntry> statusHistory;
    public StepHistoryResponse() {
    }
    public UUID getStepId() {
        return stepId;
    }
    public void setStepId(UUID stepId) {
        this.stepId = stepId;
    }
    public String getStepName() {
        return stepName;
    }
    public void setStepName(String stepName) {
        this.stepName = stepName;
    }
    public List<StepStatusHistoryEntry> getStatusHistory() {
        return statusHistory;
    }
    public void setStatusHistory(List<StepStatusHistoryEntry> statusHistory) {
        this.statusHistory = statusHistory;
    }


}

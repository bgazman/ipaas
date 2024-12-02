package consulting.gazman.ipaas.workflow.exception;

import java.util.UUID;

public class WorkflowStepNotFoundException extends RuntimeException {

    private final UUID stepId;

    public WorkflowStepNotFoundException(UUID stepId) {
        super("Workflow step not found with id: " + stepId);
        this.stepId = stepId;
    }

    public WorkflowStepNotFoundException(UUID stepId, String message) {
        super(message);
        this.stepId = stepId;
    }

    public WorkflowStepNotFoundException(UUID stepId, String message, Throwable cause) {
        super(message, cause);
        this.stepId = stepId;
    }

    public UUID getStepId() {
        return stepId;
    }
}
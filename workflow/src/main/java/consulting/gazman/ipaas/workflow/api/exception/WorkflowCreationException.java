package consulting.gazman.ipaas.workflow.api.exception;


public class WorkflowCreationException extends ApiException {
    public WorkflowCreationException(String message) {
        super(message);
    }

    public WorkflowCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
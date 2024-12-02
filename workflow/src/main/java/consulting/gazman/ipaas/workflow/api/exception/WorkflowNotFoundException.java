package consulting.gazman.ipaas.workflow.api.exception;


public class WorkflowNotFoundException extends ApiException {
    public WorkflowNotFoundException(String message) {
        super(message);
    }

    public WorkflowNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
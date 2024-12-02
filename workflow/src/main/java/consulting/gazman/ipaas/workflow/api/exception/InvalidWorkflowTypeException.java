package consulting.gazman.ipaas.workflow.api.exception;

public class InvalidWorkflowTypeException extends ApiException {
    public InvalidWorkflowTypeException(String message) {
        super(message);
    }

    public InvalidWorkflowTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
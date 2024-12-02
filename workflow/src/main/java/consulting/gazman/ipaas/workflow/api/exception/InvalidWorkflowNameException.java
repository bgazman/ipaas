package consulting.gazman.ipaas.workflow.api.exception;

public class InvalidWorkflowNameException extends ApiException {
    public InvalidWorkflowNameException(String message) {
        super(message);
    }

    public InvalidWorkflowNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
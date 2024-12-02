package consulting.gazman.ipaas.workflow.enums;

public enum WorkflowStatus {
    CREATED,
    INITIALIZED,
    QUEUED,
    STARTED,
    RUNNING,
    PENDING,
    PENDING_RETRY,
    PAUSED,
    COMPLETED,
    FAILED,
    CANCELLED,
    ERROR,
    TIMED_OUT
}
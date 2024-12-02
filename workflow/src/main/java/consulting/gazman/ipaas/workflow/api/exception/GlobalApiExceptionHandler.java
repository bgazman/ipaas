package consulting.gazman.ipaas.workflow.api.exception;

import consulting.gazman.ipaas.workflow.api.dto.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleApiException(ApiException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(null, "ERROR", ex.getMessage()));
    }

    @ExceptionHandler(InvalidWorkflowTypeException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidWorkflowTypeException(InvalidWorkflowTypeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(null, "ERROR", "Invalid workflow type: " + ex.getMessage()));
    }

    @ExceptionHandler(InvalidWorkflowNameException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidWorkflowNameException(InvalidWorkflowTypeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(null, "ERROR", "Invalid workflow name: " + ex.getMessage()));
    }
    @ExceptionHandler(WorkflowCreationException.class)
    public ResponseEntity<ApiResponse<Void>> handleWorkflowCreationException(WorkflowCreationException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(null, "ERROR", "Error creating workflow: " + ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(null, "ERROR", "An unexpected error occurred"));
    }
}
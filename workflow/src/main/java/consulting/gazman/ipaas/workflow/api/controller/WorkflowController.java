package consulting.gazman.ipaas.workflow.api.controller;

import consulting.gazman.ipaas.workflow.api.dto.common.ApiRequest;
import consulting.gazman.ipaas.workflow.api.dto.common.ApiResponse;
import consulting.gazman.ipaas.workflow.api.dto.request.WorkflowSubmitRequest;
import consulting.gazman.ipaas.workflow.api.dto.response.StepHistoryResponse;
import consulting.gazman.ipaas.workflow.api.dto.response.WorkflowDetailsResponse;
import consulting.gazman.ipaas.workflow.api.dto.response.WorkflowListResponse;
import consulting.gazman.ipaas.workflow.api.dto.response.WorkflowResubmitResponse;
import consulting.gazman.ipaas.workflow.api.dto.response.WorkflowSubmitResponse;
import consulting.gazman.ipaas.workflow.api.exception.InvalidWorkflowNameException;
import consulting.gazman.ipaas.workflow.api.exception.WorkflowCreationException;
import consulting.gazman.ipaas.workflow.service.WorkflowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/workflows")
public class WorkflowController {
    private final WorkflowService workflowService;
    private static final Logger logger = LoggerFactory.getLogger(WorkflowController.class);

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

 @PostMapping("/submit")
    public ResponseEntity<ApiResponse<WorkflowSubmitResponse>> submitWorkflow(@RequestBody ApiRequest<WorkflowSubmitRequest> apiRequest) {
        try {
            WorkflowSubmitRequest request = apiRequest.getData();
            WorkflowSubmitResponse response = workflowService.submitWorkflow(request);
            return ResponseEntity.ok(new ApiResponse<>(response, "SUCCESS", "Workflow submitted successfully"));
        } catch (InvalidWorkflowNameException e) {
            logger.error("Invalid workflow name", e);
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, "ERROR", "Invalid workflow name: " + e.getMessage()));
        } catch (WorkflowCreationException e) {
            logger.error("Error creating workflow", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse<>(null, "ERROR", "Error creating workflow: " + e.getMessage()));
        } catch (Exception e) {
            logger.error("Unexpected error submitting workflow", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse<>(null, "ERROR", "An unexpected error occurred"));
        }
    }
    
    @PostMapping("/{workflowId}/resubmit")
    public ResponseEntity<WorkflowResubmitResponse> resubmitWorkflow(@PathVariable UUID workflowId) {
        logger.info("Received a resubmit request: {}", workflowId);

        WorkflowResubmitResponse response = workflowService.resubmitWorkflow(workflowId);
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<ApiResponse<WorkflowListResponse>> getWorkflows(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String workflowName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate) {
                logger.info("Received a getWorkflows request: {}", workflowName);

        WorkflowListResponse response = workflowService.getWorkflows(page, pageSize, status, workflowName, fromDate, toDate);
        return ResponseEntity.ok(new ApiResponse<>(response, "SUCCESS", "Workflows retrieved successfully"));
    }
    
    @GetMapping("/{workflowId}")
    public ResponseEntity<WorkflowDetailsResponse> getWorkflowDetails(@PathVariable UUID workflowId) {
        WorkflowDetailsResponse response = workflowService.getWorkflowDetails(workflowId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{workflowId}/steps/{stepName}/trigger")
    public ResponseEntity<?> triggerWorkflowStep(@PathVariable UUID workflowId, 
                                                 @PathVariable String stepName,
                                                 @RequestParam(required = false) Integer stepOrder) {
        workflowService.triggerWorkflowStep(workflowId, stepName, stepOrder);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{workflowId}/steps/{stepId}/history")
public ResponseEntity<ApiResponse<StepHistoryResponse>> getStepHistory(
        @PathVariable UUID workflowId,
        @PathVariable UUID stepId) {
    StepHistoryResponse response = workflowService.getStepHistory(workflowId, stepId);
    return ResponseEntity.ok(new ApiResponse<>(response, "SUCCESS", "Step history retrieved successfully"));
}
}
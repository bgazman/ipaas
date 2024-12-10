package consulting.gazman.ipaas.workflow.api.controller;

import consulting.gazman.ipaas.workflow.api.dto.common.ApiRequest;
import consulting.gazman.ipaas.workflow.api.dto.common.ApiResponse;
import consulting.gazman.ipaas.workflow.service.WorkflowService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import consulting.gazman.ipaas.workflow.api.dto.WorkflowDto;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/workflows")
public class WorkflowController extends BaseController {

    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    // Get all workflows, optionally filter by name or status
    @GetMapping
    public ResponseEntity<ApiResponse<List<WorkflowDto>>> getWorkflows(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) String status,
            HttpServletRequest request) {

        // Extract and log metadata
        Map<String, Object> metadata = extractMetadata(request);
        logMetadata(metadata);

        // Fetch workflows
        List<WorkflowDto> workflows = workflowService.getWorkflows(name, status);
        return ResponseEntity.ok(successResponse(workflows, "Workflows retrieved successfully."));
    }

    // Get a specific workflow by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkflowDto>> getWorkflowById(
            @PathVariable UUID id,
            HttpServletRequest request) {

        // Extract and log metadata
        Map<String, Object> metadata = extractMetadata(request);
        logMetadata(metadata);

        // Fetch workflow by ID
        WorkflowDto workflow = workflowService.getWorkflowById(id);
        return ResponseEntity.ok(successResponse(workflow, "Workflow retrieved successfully."));
    }

    // Create a new workflow
    @PostMapping
    public ResponseEntity<ApiResponse<WorkflowDto>> createWorkflow(
            @RequestBody ApiRequest<WorkflowDto> request, HttpServletRequest httpServletRequest) {

        // Process metadata
        Map<String, Object> metadata = extractMetadata(httpServletRequest);
        logMetadata(metadata);

        // Create workflow
        WorkflowDto workflowDto = request.getData();
        WorkflowDto createdWorkflow = workflowService.createWorkflow(workflowDto);
        return ResponseEntity.ok(successResponse(createdWorkflow, "Workflow created successfully."));
    }

    // Update an existing workflow
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkflowDto>> updateWorkflow(
            @PathVariable UUID id,
            @RequestBody ApiRequest<WorkflowDto> request,
            HttpServletRequest httpServletRequest) {

        // Process metadata
        Map<String, Object> metadata = extractMetadata(httpServletRequest);
        logMetadata(metadata);

        // Update workflow
        WorkflowDto workflowDto = request.getData();
        WorkflowDto updatedWorkflow = workflowService.updateWorkflow(id, workflowDto);
        return ResponseEntity.ok(successResponse(updatedWorkflow, "Workflow updated successfully."));
    }

    // Delete a workflow
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteWorkflow(
            @PathVariable UUID id, HttpServletRequest request) {

        // Extract and log metadata
        Map<String, Object> metadata = extractMetadata(request);
        logMetadata(metadata);

        // Delete workflow
        workflowService.deleteWorkflow(id);
        return ResponseEntity.ok(successResponse(null, "Workflow deleted successfully."));
    }

}

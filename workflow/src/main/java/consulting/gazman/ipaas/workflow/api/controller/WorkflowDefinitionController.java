package consulting.gazman.ipaas.workflow.api.controller;

import consulting.gazman.ipaas.workflow.api.dto.WorkflowDefinitionDto;
import consulting.gazman.ipaas.workflow.api.dto.common.ApiRequest;
import consulting.gazman.ipaas.workflow.api.dto.common.ApiResponse;
import consulting.gazman.ipaas.workflow.service.WorkflowDefinitionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/workflow-definitions")
public class WorkflowDefinitionController extends BaseController {

    private final WorkflowDefinitionService workflowDefinitionService;

    public WorkflowDefinitionController(WorkflowDefinitionService workflowDefinitionService) {
        this.workflowDefinitionService = workflowDefinitionService;
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<WorkflowDefinitionDto>>> getAllWorkflowDefinitions(
            @RequestParam(value = "domain", required = false) String domain) {
        // Fetch workflow definitions, optionally filtering by domain
        List<WorkflowDefinitionDto> workflowDefinitions = workflowDefinitionService.getAllWorkflows(domain);
        return ResponseEntity.ok(new ApiResponse<>(workflowDefinitions, "SUCCESS", "Workflow definitions retrieved successfully."));
    }

    @GetMapping("/{domain}/{name}")
    public ResponseEntity<ApiResponse<WorkflowDefinitionDto>> getWorkflowDefinition(
            @PathVariable String domain,
            @PathVariable String name, HttpServletRequest request) {
        // Extract and log metadata
        Map<String, Object> metadata = extractMetadata(request);
        logMetadata(metadata);

        // Fetch workflow definition and return success response
        WorkflowDefinitionDto workflow = workflowDefinitionService.getWorkflowByDomainAndName(domain, name);
        return ResponseEntity.ok(successResponse(workflow, "Workflow definition retrieved successfully."));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<WorkflowDefinitionDto>> createWorkflowDefinition(
            @RequestBody ApiRequest<WorkflowDefinitionDto> request, HttpServletRequest httpServletRequest) {
        // Process metadata
        Map<String, Object> metadata = extractMetadata(httpServletRequest);
        logMetadata(metadata);

        // Create workflow definition
        WorkflowDefinitionDto workflowDto = request.getData();
        WorkflowDefinitionDto createdWorkflow = workflowDefinitionService.createWorkflow(workflowDto);

        return ResponseEntity.ok(successResponse(createdWorkflow, "Workflow definition created successfully."));
    }


}

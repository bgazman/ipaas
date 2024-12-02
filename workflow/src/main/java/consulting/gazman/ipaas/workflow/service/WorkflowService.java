package consulting.gazman.ipaas.workflow.service;

import consulting.gazman.ipaas.workflow.api.dto.request.WorkflowSubmitRequest;
import consulting.gazman.ipaas.workflow.api.dto.response.StepHistoryResponse;
import consulting.gazman.ipaas.workflow.api.dto.response.WorkflowDetailsResponse;
import consulting.gazman.ipaas.workflow.api.dto.response.WorkflowListResponse;
import consulting.gazman.ipaas.workflow.api.dto.response.WorkflowResubmitResponse;
import consulting.gazman.ipaas.workflow.api.dto.response.WorkflowSubmitResponse;
import consulting.gazman.ipaas.workflow.enums.WorkflowType;
import consulting.gazman.ipaas.workflow.model.Workflow;
import java.time.LocalDateTime;
import java.util.UUID;

public interface WorkflowService {
    void processWorkflow(UUID workflowId, WorkflowType workflowType);
    WorkflowSubmitResponse submitWorkflow(WorkflowSubmitRequest request);
    WorkflowResubmitResponse resubmitWorkflow(UUID workflowId);
    WorkflowDetailsResponse getWorkflowDetails(UUID workflowId);
    Workflow getWorkflowById(UUID workflowId);
    void triggerWorkflowStep(UUID workflowId, String stepName, Integer stepOrder);
    WorkflowListResponse getWorkflows(int page, int pageSize, String status,
    String workflowName, LocalDateTime fromDate, LocalDateTime toDate);
    StepHistoryResponse getStepHistory(UUID workflowId, UUID stepId);

}
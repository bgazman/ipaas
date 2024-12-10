package consulting.gazman.ipaas.workflow.service;


import consulting.gazman.ipaas.workflow.api.dto.WorkflowDto;

import java.util.List;
import java.util.UUID;

public interface WorkflowService {
    List<WorkflowDto> getWorkflows(String name, String status);

    WorkflowDto getWorkflowById(UUID id);

    WorkflowDto createWorkflow(WorkflowDto workflowDto);

    WorkflowDto updateWorkflow(UUID id, WorkflowDto workflowDto);

    void deleteWorkflow(UUID id);
}

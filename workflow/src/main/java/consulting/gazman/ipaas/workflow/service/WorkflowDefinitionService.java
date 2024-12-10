package consulting.gazman.ipaas.workflow.service;

import consulting.gazman.ipaas.workflow.api.dto.WorkflowDefinitionDto;

import java.util.List;
import java.util.UUID;

public interface WorkflowDefinitionService {
    List<WorkflowDefinitionDto> getAllWorkflows(String domain);

    WorkflowDefinitionDto getWorkflowByDomainAndName(String domain, String name);

    WorkflowDefinitionDto createWorkflow(WorkflowDefinitionDto workflowDto);

    WorkflowDefinitionDto updateWorkflow(UUID id, WorkflowDefinitionDto workflowDto);

    void deleteWorkflow(UUID id);
}

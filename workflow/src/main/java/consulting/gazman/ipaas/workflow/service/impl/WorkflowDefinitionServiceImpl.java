package consulting.gazman.ipaas.workflow.service.impl;



import consulting.gazman.ipaas.workflow.api.dto.WorkflowDefinitionDto;
import consulting.gazman.ipaas.workflow.entity.WorkflowDefinition;
import consulting.gazman.ipaas.workflow.repository.WorkflowDefinitionRepository;
import consulting.gazman.ipaas.workflow.service.WorkflowDefinitionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WorkflowDefinitionServiceImpl implements WorkflowDefinitionService {

    private final WorkflowDefinitionRepository repository;

    public WorkflowDefinitionServiceImpl(WorkflowDefinitionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<WorkflowDefinitionDto> getAllWorkflows(String domain) {
        List<WorkflowDefinition> workflows = domain != null
                ? repository.findByDomain(domain)
                : repository.findAll();

        return workflows.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public WorkflowDefinitionDto getWorkflowByDomainAndName(String domain, String name) {
        WorkflowDefinition workflow = repository.findByDomainAndName(domain, name)
                .orElseThrow(() -> new IllegalArgumentException("Workflow not found"));
        return mapToDto(workflow);
    }

    @Override
    public WorkflowDefinitionDto createWorkflow(WorkflowDefinitionDto workflowDto) {
        boolean exists = repository.findByDomainAndName(workflowDto.getDomain(), workflowDto.getName())
                .isPresent();

        if (exists) {
            throw new IllegalArgumentException("Workflow with the same name and domain already exists.");
        }

        WorkflowDefinition workflow = mapToEntity(workflowDto);
        WorkflowDefinition savedWorkflow = repository.save(workflow);
        return mapToDto(savedWorkflow);
    }


    @Override
    public WorkflowDefinitionDto updateWorkflow(UUID id, WorkflowDefinitionDto workflowDto) {
        WorkflowDefinition workflow = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Workflow not found"));
        workflow.setDomain(workflowDto.getDomain());
        workflow.setName(workflowDto.getName());
        workflow.setType(workflowDto.getType());
        workflow.setVersion(workflowDto.getVersion());
        workflow.setDefinition(workflowDto.getDefinition());
        workflow.setActive(workflowDto.isActive());
        WorkflowDefinition updatedWorkflow = repository.save(workflow);
        return mapToDto(updatedWorkflow);
    }

    @Override
    public void deleteWorkflow(UUID id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Workflow not found");
        }
        repository.deleteById(id);
    }

    private WorkflowDefinition mapToEntity(WorkflowDefinitionDto dto) {
        WorkflowDefinition workflow = new WorkflowDefinition();
        workflow.setId(dto.getId());
        workflow.setDomain(dto.getDomain());
        workflow.setName(dto.getName());
        workflow.setType(dto.getType());
        workflow.setVersion(dto.getVersion());
        workflow.setDefinition(dto.getDefinition());
        workflow.setActive(dto.isActive());
        return workflow;
    }

    private WorkflowDefinitionDto mapToDto(WorkflowDefinition workflow) {
        WorkflowDefinitionDto dto = new WorkflowDefinitionDto();
        dto.setId(workflow.getId());
        dto.setDomain(workflow.getDomain());
        dto.setName(workflow.getName());
        dto.setType(workflow.getType());
        dto.setVersion(workflow.getVersion());
        dto.setDefinition(workflow.getDefinition());
        dto.setActive(workflow.isActive());
        dto.setCreatedAt(workflow.getCreatedAt());
        dto.setUpdatedAt(workflow.getUpdatedAt());
        return dto;
    }
}

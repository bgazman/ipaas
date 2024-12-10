package consulting.gazman.ipaas.workflow.service.impl;

import consulting.gazman.ipaas.workflow.entity.Workflow;
import consulting.gazman.ipaas.workflow.repository.WorkflowRepository;
import consulting.gazman.ipaas.workflow.service.WorkflowService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import java.util.List;
import consulting.gazman.ipaas.workflow.api.dto.WorkflowDto;




@Service
public class WorkflowServiceImpl implements WorkflowService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    private final WorkflowRepository workflowRepository;

    public WorkflowServiceImpl(WorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    @Override
    public List<WorkflowDto> getWorkflows(String name, String status) {
        List<Workflow> workflows;
        if (name != null && status != null) {
            workflows = workflowRepository.findByNameAndStatus(name, status);
        } else if (name != null) {
            workflows = workflowRepository.findByName(name);
        } else if (status != null) {
            workflows = workflowRepository.findByStatus(status);
        } else {
            workflows = workflowRepository.findAll();
        }
        return workflows.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public WorkflowDto getWorkflowById(UUID id) {
        Workflow workflow = workflowRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Workflow not found"));
        return mapToDto(workflow);
    }

    @Override
    public WorkflowDto createWorkflow(WorkflowDto workflowDto) {
        Workflow workflow = mapToEntity(workflowDto);
        Workflow savedWorkflow = workflowRepository.save(workflow);
        return mapToDto(savedWorkflow);
    }

    @Override
    public WorkflowDto updateWorkflow(UUID id, WorkflowDto workflowDto) {
        Workflow workflow = workflowRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Workflow not found"));
        workflow.setStatus(workflowDto.getStatus());
        workflow.setRetryCount(workflowDto.getRetryCount());
        workflow.setUpdatedBy(workflowDto.getUpdatedBy());
        Workflow updatedWorkflow = workflowRepository.save(workflow);
        return mapToDto(updatedWorkflow);
    }

    @Override
    public void deleteWorkflow(UUID id) {
        if (!workflowRepository.existsById(id)) {
            throw new IllegalArgumentException("Workflow not found");
        }
        workflowRepository.deleteById(id);
    }

    private Workflow mapToEntity(WorkflowDto dto) {
        Workflow workflow = new Workflow();
        workflow.setName(dto.getName());
        workflow.setCorrelationId(dto.getCorrelationId());
        workflow.setStatus(dto.getStatus());
        workflow.setType(dto.getType());
        workflow.setMaxRetries(dto.getMaxRetries());
        workflow.setRetryCount(dto.getRetryCount());
        workflow.setSlaDeadline(dto.getSlaDeadline() != null
                ? LocalDateTime.parse(dto.getSlaDeadline(), FORMATTER)
                : null);        workflow.setCreatedBy(dto.getCreatedBy());
        workflow.setUpdatedBy(dto.getUpdatedBy());
        workflow.setPriority(dto.getPriority());
        workflow.setParentWorkflowId(dto.getParentWorkflowId());
        return workflow;
    }

    private WorkflowDto mapToDto(Workflow entity) {
        WorkflowDto dto = new WorkflowDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCorrelationId(entity.getCorrelationId());
        dto.setStatus(entity.getStatus());
        dto.setType(entity.getType());
        dto.setMaxRetries(entity.getMaxRetries());
        dto.setRetryCount(entity.getRetryCount());
        dto.setSlaDeadline(String.valueOf(entity.getSlaDeadline()));
        dto.setCreatedAt(String.valueOf(entity.getCreatedAt()));
        dto.setUpdatedAt(String.valueOf(entity.getUpdatedAt()));
        dto.setCompletedAt(String.valueOf(entity.getCompletedAt()));
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setPriority(entity.getPriority());
        dto.setParentWorkflowId(entity.getParentWorkflowId());
        return dto;
    }
}

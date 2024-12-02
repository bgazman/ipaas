package consulting.gazman.ipaas.workflow.service;

import consulting.gazman.ipaas.workflow.model.WorkflowStep;
import consulting.gazman.ipaas.workflow.model.Workflow;
import consulting.gazman.ipaas.workflow.repository.WorkflowStepRepository;
import consulting.gazman.ipaas.workflow.exception.WorkflowStepNotFoundException;
import consulting.gazman.ipaas.workflow.enums.StepStatus;

import org.springframework.stereotype.Service;


import java.util.UUID;
import java.time.LocalDateTime;

@Service
public class WorkflowStepService {
    private final WorkflowStepRepository workflowStepRepository;

    public WorkflowStepService(WorkflowStepRepository workflowStepRepository) {
        this.workflowStepRepository = workflowStepRepository;
    }

    public WorkflowStep createStep(UUID workflowId, String name, int order) {
        WorkflowStep step = new WorkflowStep();
        step.setWorkflow(new Workflow());
        step.setStepName(name);
        step.setStepOrder(order);
        step.setStatus(StepStatus.PENDING.name());
        return workflowStepRepository.save(step);
    }

    public WorkflowStep updateStepStatus(UUID stepId, StepStatus status) {
        
        WorkflowStep step = workflowStepRepository.findById(stepId)
            .orElseThrow(() -> new WorkflowStepNotFoundException(stepId));
        step.setStatus(status.name());
        if (status == StepStatus.IN_PROGRESS) {
            step.setStartedAt(LocalDateTime.now());
        } else if (status == StepStatus.COMPLETED) {
            step.setCompletedAt(LocalDateTime.now());
        }
        return workflowStepRepository.save(step);
    }

    // public List<WorkflowStep> getStepsByWorkflowId(UUID workflowId) {
    //     return workflowStepRepository.findByWorkflowIdOrderByOrderAsc(workflowId);
    // }
}
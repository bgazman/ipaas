package consulting.gazman.ipaas.workflow.orchestrator;

import consulting.gazman.ipaas.workflow.repository.WorkflowRepository;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import consulting.gazman.ipaas.workflow.model.Workflow;
import consulting.gazman.ipaas.workflow.model.WorkflowDefinition;
import consulting.gazman.ipaas.workflow.enums.WorkflowStatus;
import consulting.gazman.ipaas.workflow.model.WorkflowStep;
import consulting.gazman.ipaas.workflow.repository.WorkflowPayloadRepository;
import consulting.gazman.ipaas.workflow.repository.WorkflowStepRepository;

import java.util.UUID;

import consulting.gazman.ipaas.workflow.api.exception.WorkflowNotFoundException;
import consulting.gazman.ipaas.workflow.messaging.producer.WorkflowMessageProducer;

import java.util.List;
import java.time.LocalDateTime;
import jakarta.transaction.Transactional;
import consulting.gazman.ipaas.workflow.enums.StepStatus;

@Service
public class WorkflowOrchestrator  {
        protected final Logger logger = LoggerFactory.getLogger(WorkflowOrchestrator.class);
    @Autowired 
    private WorkflowMessageProducer messageProducer;
    private final WorkflowRepository workflowRepository;
    private final WorkflowPayloadRepository workflowPayloadRepository;
    public WorkflowOrchestrator(WorkflowRepository workflowRepository,
            WorkflowPayloadRepository workflowPayloadRepository, WorkflowStepRepository workflowStepRepository) {
        this.workflowRepository = workflowRepository;
        this.workflowPayloadRepository = workflowPayloadRepository;
        this.workflowStepRepository = workflowStepRepository;
    }

    private final WorkflowStepRepository workflowStepRepository;

    // Constructor with dependencies injection

    @Transactional
    public UUID startWorkflow(WorkflowDefinition workflowDefinition, UUID workflowId) {
        logger.info("Starting workflow: {}", workflowId);
    
        Workflow workflow = workflowRepository.findById(workflowId)
            .orElseThrow(() -> new WorkflowNotFoundException("Workflow not found: {} " ));
    
        logger.info("Initializing workflow definition: {}", workflowDefinition.getClass().getSimpleName());
        workflowDefinition.initialize(workflow);
    
        logger.info("Defining steps for: {}", workflowDefinition.getClass().getSimpleName());
        List<WorkflowStep> steps = workflowDefinition.defineSteps(workflow);
    
        logger.info("Saving {} steps for workflow: {}", steps.size(), workflowId);
        for (WorkflowStep step : steps) {
            workflowStepRepository.save(step);
        }
    
        workflow.setStatus(WorkflowStatus.STARTED.name());
        workflowRepository.save(workflow);
    
        logger.info("Workflow started: {}", workflowId);
    
        // Trigger the first step
        if (!steps.isEmpty()) {
            WorkflowStep firstStep = steps.get(0);
            triggerNextStep(firstStep);
        } else {
            logger.warn("No steps defined for workflow: {}", workflowId);
        }
    
        return workflow.getId();
    }
    
    private void triggerNextStep(WorkflowStep step) {
        logger.info("Triggering next step: {} for workflow: {}", step.getStepName(), step.getWorkflow().getId());
        messageProducer.sendStepStartMessage( step.getStepName(), step.getWorkflow().getId());
    }



    // public void startNextStep(UUID workflowId) {
    //     WorkflowStep nextStep = findNextStep(workflowId);
    //     if (nextStep != null) {
    //         publishNextStepMessage(nextStep);
    //         logger.info("Published start message for step: {} of workflow: {}", 
    //                     nextStep.getStepName(), workflowId);
    //     } else {
    //         logger.info("No more steps to execute for workflow: {}", workflowId);
    //         // Here you might want to update the workflow status to COMPLETED
    //     }
    // }
    private void executeWorkflow(UUID workflowId) {
        // Implementation of workflow execution
    }
private void startStepGroup(List<WorkflowStep> steps, UUID workflowId) {
    for (WorkflowStep step : steps) {
        step.setStatus(StepStatus.IN_PROGRESS.name());
        step.setStartedAt(LocalDateTime.now());
        workflowStepRepository.save(step);
        // messagePublisher.publishStepStartMessage(workflowId, step.getStepId());
    }
}
}

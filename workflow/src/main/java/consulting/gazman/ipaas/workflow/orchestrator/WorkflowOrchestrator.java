package consulting.gazman.ipaas.workflow.orchestrator;

import consulting.gazman.ipaas.workflow.messaging.model.WorkflowMessage;
import consulting.gazman.ipaas.workflow.repository.WorkflowRepository;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import consulting.gazman.ipaas.workflow.model.Workflow;
import consulting.gazman.ipaas.workflow.model.WorkflowDefinition;
import consulting.gazman.ipaas.workflow.enums.WorkflowStatus;
import consulting.gazman.ipaas.workflow.implementations.oms.workflow.SubmitOrderWorkflowDefinition;
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

    @Async
    public void handleWorkflowEvent(WorkflowMessage workflowMessage){
        // Extract the workflowId from the message
        UUID workflowId;
        try {
            workflowId = workflowMessage.getWorkflowId();
        } catch (IllegalArgumentException e) {
            logger.error("Invalid workflowId in WorkflowMessage", e);
            return;
        }
        Workflow workflow = null;
        try{
            workflow = workflowRepository.findById(workflowId)
            .orElseThrow(() -> new WorkflowNotFoundException("Workflow not found" ));
        }catch(WorkflowNotFoundException e){
            logger.error("Workflow not found", e);
            return;
        }
        if(workflow.getStatus().equals( WorkflowStatus.SUBMITTED.name())){
                createWorkflowSteps(workflow);
                workflowRepository.findById(workflowId).get().getSteps(); 
                workflow.setStatus(WorkflowStatus.CREATED.name());
                workflowRepository.save(workflow);
                startWorkflowExecution(workflowId);
                return;
            }
        if(workflow.getStatus().equals( WorkflowStatus.RUNNING.name())){
            
        }

    }

    @Transactional
    public void createWorkflowSteps(Workflow workflow) {

        UUID workflowId = workflow.getId();
        logger.info("Starting workflow: {}", workflowId);
         // Create the appropriate WorkflowDefinition
        WorkflowDefinition workflowDefinition = getWorkflowDefinition();
        
        logger.info("Initializing workflow definition: {}", workflowDefinition.getClass().getSimpleName());
        workflowDefinition.initialize(workflow);
    
        logger.info("Defining steps for: {}", workflowDefinition.getClass().getSimpleName());
        List<WorkflowStep> steps = workflowDefinition.defineSteps(workflow);
    
        logger.info("Saving {} steps for workflow: {}", steps.size(), workflowId);
        for (WorkflowStep step : steps) {
            workflowStepRepository.save(step);
        }
    


        logger.info("{} : Workflow created", workflowId);
    

    }
    
    public void startWorkflowExecution(UUID workflowId){

            
        WorkflowStep step = workflowStepRepository.findFirstByWorkflowIdOrderByStepOrderAsc(workflowId);
        WorkflowMessage workflowMessage = new WorkflowMessage();
        workflowMessage.setStepId(step.getId());
        workflowMessage.setStepName(step.getStepName());
        workflowMessage.setAction("START");
        workflowMessage.setData("dd");
        messageProducer.sendStepEvent(workflowMessage);
        logger.info("{} : Triggering workflow execution", workflowId);

    }
//    private void triggerStepExecution(WorkflowStep step) {
//        logger.info("Triggering next step: {} for workflow: {}", step.getStepName(), step.getWorkflow().getId());
//        messageProducer.sendStepStartMessage( step.getStepName(), step.getId());
//    }

    private WorkflowDefinition getWorkflowDefinition() {
        return new SubmitOrderWorkflowDefinition();
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

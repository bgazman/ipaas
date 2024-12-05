package consulting.gazman.ipaas.workflow.service;

import consulting.gazman.ipaas.workflow.messaging.model.WorkflowMessage;
import consulting.gazman.ipaas.workflow.messaging.model.WorkflowStepMessage;
import consulting.gazman.ipaas.workflow.model.WorkflowPayload;
import consulting.gazman.ipaas.workflow.model.WorkflowStep;
import consulting.gazman.ipaas.workflow.repository.WorkflowPayloadRepository;
import consulting.gazman.ipaas.workflow.repository.WorkflowStepRepository;
import jakarta.transaction.Transactional;
import consulting.gazman.ipaas.workflow.messaging.producer.WorkflowMessageProducer;
import consulting.gazman.ipaas.workflow.exception.WorkflowStepNotFoundException;
import consulting.gazman.ipaas.workflow.config.QueueInitializer;
import consulting.gazman.ipaas.workflow.enums.StepStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.LocalDateTime;

import consulting.gazman.ipaas.workflow.exception.WorkflowNotFoundException;

public abstract class WorkflowStepService{
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());
    public final static String SUCCESS="SUCCESS";
    @Autowired
    protected  WorkflowStepRepository workflowStepRepository;
    @Autowired
    protected WorkflowMessageProducer messageProducer;
    @Autowired
    protected WorkflowPayloadRepository payloadRepository;

    public void updateStep(WorkflowStep step) {
        

        String status = step.getStatus();

        if (status.equals(StepStatus.IN_PROGRESS.name()) ) {
            step.setStartedAt(LocalDateTime.now());
        } else if (status.equals(StepStatus.COMPLETED.name())) {
            step.setCompletedAt(LocalDateTime.now());
        }
        workflowStepRepository.save(step);
    }


    @Async
    public void handleStepEvent(WorkflowMessage message) {
        // Extract the workflowId from the message
        UUID stepId;
        try {
            stepId = message.getStepId();

        } catch (IllegalArgumentException e) {
            logger.error("Invalid workflowId in WorkflowMessage", e);
            return;
        }
        WorkflowStep step = null;
       try{
        step  = workflowStepRepository.findById(stepId)
        .orElseThrow(() -> new WorkflowStepNotFoundException(stepId));
       }catch(WorkflowStepNotFoundException e){
            logger.error("{} Unable to find step in db",stepId, e);
       }
        if(step == null){
             //handleStepFailure(step,"StepNoFound");
             return;
            }
        step.setStartedAt(LocalDateTime.now());
        WorkflowPayload payload = payloadRepository.findByWorkflowId(step.getWorkflow().getId());   
        //Implement Business Logic    
        String businessLogicResult = handleBusinessLogic(payload.getPayload());    
        step.setCompletedAt(LocalDateTime.now());
        if(!businessLogicResult.equals(SUCCESS)){
            handleStepFailure(step, businessLogicResult);
        }
        updateStep(step);

        messageProducer.sendWorkflowEvent(step.getWorkflow());    }
   
    public String handleBusinessLogic(String payload){
        return "";
    }


//    public void notifyWorkflow(UUID workflowId,String workflowName,String action) {
//        WorkflowMessage<String> workflowMessage = new WorkflowMessage<>();
//        workflowMessage.setWorkflowId(workflowId);
//        workflowMessage.setWorkflowName(workflowName);
//        workflowMessage.setAction(action);
//        messageProducer.sendWorkflowEvent(workflowMessage);
//    }




    public WorkflowStep getStepByWorkflowId(UUID workflowId) {
        List<WorkflowStep> steps = workflowStepRepository.findByWorkflowId(workflowId);

        return steps.stream()
            .filter(step -> !Objects.equals(step.getStatus(), StepStatus.COMPLETED.name()) && step.getStatus() != StepStatus.FAILED.name())
            .sorted(Comparator.comparing(WorkflowStep::getStepOrder).reversed()
                             .thenComparing(WorkflowStep::getCreatedAt).reversed())
            .findFirst()
            .orElseThrow(() -> new WorkflowStepNotFoundException(workflowId));
    }


    public void handleStepFailure(WorkflowStep step,String reason) {
        
        //if retry 
        if(Objects.equals(step.getRetryCount(), step.getMaxRetries())){
            step.setStatus(StepStatus.FAILED.name());
        }else{
            step.setStatus(StepStatus.PENDING_RETRY.name());            
            messageProducer.sendToDLQwithTTL(step.getStepName(), step.getId(), calculateTTL(step.getRetryCount()));
        }

    }

    private long calculateTTL(int retryCount) {
        // Base delay in milliseconds (e.g., 1 second)
        long baseDelay = 20000;
        
        // Maximum delay to cap the backoff (e.g., 1 hour)
        long maxDelay = 3600000;
        
        // Calculate delay with exponential backoff
        long delay = baseDelay * (long) Math.pow(2, retryCount);
        
        // Add some random jitter to avoid thundering herd problem
        long jitter = (long) (Math.random() * baseDelay);
        
        // Cap the delay at the maximum value
        return Math.min(delay + jitter, maxDelay);
    }

}
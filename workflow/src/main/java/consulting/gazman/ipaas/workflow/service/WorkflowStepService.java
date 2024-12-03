package consulting.gazman.ipaas.workflow.service;

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
import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Comparator;
import java.util.Optional;

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

        if (status == StepStatus.IN_PROGRESS.name()) {
            step.setStartedAt(LocalDateTime.now());
        } else if (status == StepStatus.COMPLETED.name()) {
            step.setCompletedAt(LocalDateTime.now());
        }
        workflowStepRepository.save(step);
    }


    @Async
    public void executeStep(UUID workflowId,String stepName) {
        logger.info("Executing Step: " + stepName +"for workflow: " + workflowId );
        WorkflowStep step = getStepByWorkflowId(workflowId);
        if(step == null){
             handleStepFailure(step,"StepNoFound");
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
        notifyWorkflow(step.getWorkflow().getId(),step.getWorkflow().getName());
    }
   
    public String handleBusinessLogic(String payload){
        return "";
    }


    public void notifyWorkflow(UUID workflowId,String workflowName) {
        messageProducer.sendWorkflowRunningMessage(workflowId,workflowName);
    }




    public WorkflowStep getStepByWorkflowId(UUID workflowId) {
        List<WorkflowStep> steps = workflowStepRepository.findByWorkflowId(workflowId);

        return steps.stream()
            .filter(step -> step.getStatus() != StepStatus.COMPLETED.name() && step.getStatus() != StepStatus.FAILED.name())
            .sorted(Comparator.comparing(WorkflowStep::getStepOrder).reversed()
                             .thenComparing(WorkflowStep::getCreatedAt).reversed())
            .findFirst()
            .orElseThrow(() -> new WorkflowStepNotFoundException(workflowId));
    }


    public void handleStepFailure(WorkflowStep step,String reason) {
        
        //if retry 
        if(step.getRetryCount() == step.getMaxRetries()){
            step.setStatus(StepStatus.FAILED.name());
        }else{
            step.setStatus(StepStatus.PENDING_RETRY.name());            
            messageProducer.sendToDLQwithTTL(reason, step.getWorkflow().getId(), calculateTTL(step.getRetryCount()));
        }

    }

    private long calculateTTL(int retryCount) {
        // Base delay in milliseconds (e.g., 1 second)
        long baseDelay = 1000;
        
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
package consulting.gazman.ipaas.workflow.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import consulting.gazman.ipaas.workflow.service.WorkflowStepService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.MessageConverter;
import consulting.gazman.ipaas.workflow.messaging.model.WorkflowMessage;
public abstract class StepMessageConsumer  {

private final WorkflowStepService workflowStepService;
    protected final ObjectMapper objectMapper;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected StepMessageConsumer(WorkflowStepService workflowStepService,ObjectMapper objectMapper) {
        this.workflowStepService = workflowStepService;
        this.objectMapper = objectMapper;
    }


    protected WorkflowMessage convertMessage(Message message) {
        WorkflowMessage workflowMessage = null;
        try {
            workflowMessage = objectMapper.readValue(message.getBody(), new TypeReference<WorkflowMessage>() {
            });
        } catch (Exception e) {
            logger.error("Failed to convert message", e);
        }
        return workflowMessage;
    }
    

    protected void receiveMessage(Message message) {
        logger.info("Received message in queue {}", getQueueName());
        try {
            WorkflowMessage workflowMessage = convertMessage(message);
            processMessage(workflowMessage);
            

        }catch(Exception e){


        }
    }
    protected String getQueueName(){
        return "";
      }
    protected void processMessage(WorkflowMessage message) {
        workflowStepService.handleStepEvent(message);
    }

    protected long calculateTTL(int retryCount) {
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
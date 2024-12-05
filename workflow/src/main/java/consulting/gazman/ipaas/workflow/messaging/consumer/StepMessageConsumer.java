package consulting.gazman.ipaas.workflow.messaging.consumer;

import com.rabbitmq.client.Channel;
import consulting.gazman.ipaas.workflow.service.WorkflowStepService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.MessageConverter;
import consulting.gazman.ipaas.workflow.messaging.model.WorkflowMessage;
public abstract class StepMessageConsumer extends  AbstractMessageConsumer<WorkflowMessage> {

private final WorkflowStepService workflowStepService;
    protected StepMessageConsumer(MessageConverter messageConverter,WorkflowStepService workflowStepService) {

        super(messageConverter);
        this.workflowStepService = workflowStepService;
    }


//    @Override
//    protected WorkflowMessage convertMessage(Message message) {
//        if (message instanceof WorkflowMessage) {
//            return (WorkflowMessage) message;
//        } else {
//            throw new IllegalArgumentException("Expected WorkflowMessage, but received: " + payload.getClass());
//        }
//    }
    

public void receiveMessage(String rawMessage, Channel channel, Message message) {
    super.receiveMessage(rawMessage, channel, message);
}

    @Override
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
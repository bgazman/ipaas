package consulting.gazman.ipaas.workflow.implementations.oms.workflow;

import consulting.gazman.ipaas.workflow.messaging.consumer.WorkflowMessageConsumer;
import consulting.gazman.ipaas.workflow.messaging.model.WorkflowMessage;
import consulting.gazman.ipaas.workflow.orchestrator.WorkflowOrchestrator;

import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SubmitOrderConsumer extends WorkflowMessageConsumer {
//    private final WorkflowOrchestrator workflowOrchestrator;
    private static final String QUEUE_NAME = "core.workflow.submit-order.start";

    public SubmitOrderConsumer(WorkflowOrchestrator workflowOrchestrator,ObjectMapper objectMapper) {
        super( workflowOrchestrator,objectMapper);
    }


    @Override
    @RabbitListener(queues = QUEUE_NAME)
    public void receiveMessage(Message message) {
        super.receiveMessage( message);

    }



//    @Override
//    protected void processMessage(WorkflowMessage<T> message) {
//        logger.info("{} : SubmitOrderEvent Received", message.getWorkflowId());
//        workflowOrchestrator.handleWorkflowEvent(message.getWorkflowId());
//    }

    @Override
    protected String getQueueName() {
        return QUEUE_NAME;
    }
}
package consulting.gazman.ipaas.workflow.implementations.oms.workflow;

import consulting.gazman.ipaas.workflow.messaging.consumer.WorkflowMessageConsumer;
import consulting.gazman.ipaas.workflow.model.WorkflowDefinition;
import consulting.gazman.ipaas.workflow.orchestrator.WorkflowOrchestrator;

import java.util.UUID;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
@Component
public class SubmitOrderStartConsumer extends WorkflowMessageConsumer {
    private final WorkflowOrchestrator workflowOrchestrator;

    private static final String QUEUE_NAME = "core."+ "workflow.submit-order.start";

    public SubmitOrderStartConsumer(WorkflowOrchestrator workflowOrchestrator, MessageConverter messageConverter) {
        super(messageConverter);
        this.workflowOrchestrator = workflowOrchestrator;
    }

    @Override
    @RabbitListener(queues = QUEUE_NAME)
    public void receiveMessage(String rawMessage, Channel channel, Message message) {
        super.receiveMessage(rawMessage, channel, message);

    }

    @Override
    protected void processMessage(UUID workflowId) {
         workflowOrchestrator.handleWorkflowEvent(workflowId);

        logger.info("SubmitOrderWorkflow started and first step triggered for workflow: {}", workflowId);
    }

    @Override
    protected String getQueueName() {
        return QUEUE_NAME;
    }
}
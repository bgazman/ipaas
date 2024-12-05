package consulting.gazman.ipaas.workflow.implementations.oms.step;

import com.rabbitmq.client.Channel;

import consulting.gazman.ipaas.workflow.messaging.consumer.StepMessageConsumer;
import consulting.gazman.ipaas.workflow.messaging.model.WorkflowMessage;
import consulting.gazman.ipaas.workflow.service.WorkflowStepService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.UUID;


@Component
public class ValidateOrderStartConsumer {
//    private final WorkflowStepService workflowStepService;
//    private static final String QUEUE_NAME = "core.step.validate-order.start";
//    private static final String STEP_NAME = "validate-order";
//
//    @Autowired
//    protected ValidateOrder validateOrder;
//    protected ValidateOrderStartConsumer(MessageConverter messageConverter,WorkflowStepService workflowStepService) {
//        super(messageConverter,workflowStepService);
//        this.workflowStepService = workflowStepService;
//    }
//
//
//
//
//
//
//    @Override
//    protected String getQueueName() {
//        return QUEUE_NAME;
//    }
//
//    @Override
//    @RabbitListener(queues = QUEUE_NAME)
//    public void receiveMessage(String rawMessage, Channel channel, Message message) {
//        super.receiveMessage(rawMessage, channel, message);
//
//    }

}

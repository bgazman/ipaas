package consulting.gazman.ipaas.workflow.implementations.oms.step;

import com.rabbitmq.client.Channel;
import consulting.gazman.ipaas.workflow.messaging.consumer.AbstractMessageConsumer;
import consulting.gazman.ipaas.workflow.messaging.consumer.StepMessageConsumer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;

import java.util.UUID;

public class ValidateOrderStartConsumer extends StepMessageConsumer {

    private static final String QUEUE_NAME = "core."+ "step.validate-order.start";

    protected ValidateOrderStartConsumer(MessageConverter messageConverter) {
        super(messageConverter);
    }

    @Override
    protected void processMessage(UUID message) {

    }

    @Override
    protected String getQueueName() {
        return "";
    }

    @Override
    @RabbitListener(queues = QUEUE_NAME)
    public void receiveMessage(String rawMessage, Channel channel, Message message) {
        super.receiveMessage(rawMessage, channel, message);

    }

}

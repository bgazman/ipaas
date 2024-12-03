package consulting.gazman.ipaas.workflow.messaging.consumer;
import org.springframework.amqp.support.converter.MessageConverter;
import java.util.UUID;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
public abstract class WorkflowMessageConsumer extends AbstractMessageConsumer<UUID> {

    protected WorkflowMessageConsumer(MessageConverter messageConverter) {
        super(messageConverter);
    }

@Override
protected UUID convertMessage(Object payload) {
    if (payload instanceof UUID) {
        return (UUID) payload;
    } else {
        throw new IllegalArgumentException("Expected UUID, but received: " + payload.getClass());
    }

}

public void receiveMessage(String rawMessage, Channel channel, Message message) {
    super.receiveMessage(rawMessage, channel, message);
}
}
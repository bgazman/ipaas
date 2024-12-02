package consulting.gazman.ipaas.workflow.messaging.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.MessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class AbstractMessageConsumer<T> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final MessageConverter messageConverter;

    protected AbstractMessageConsumer(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    protected abstract void processMessage(T message);
    protected abstract T convertMessage(Object payload);
    protected abstract String getQueueName();

    protected void receiveMessage(String rawMessage, Channel channel, Message message) {
        logger.info("Received message in queue {}", getQueueName());
        try {
            Object payload = messageConverter.fromMessage(message);
            T convertedPayload = convertMessage(payload);
            processMessage(convertedPayload);
            
            // Acknowledge the message after successful processing
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            logger.debug("Message acknowledged: {}", rawMessage);
        } catch (Exception e) {
            // If an error occurs, reject the message and send to DLQ
            try {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                logger.error("Message rejected and moved to DLQ", e);
            } catch (IOException ioException) {
                logger.error("Failed to reject the message", ioException);
            }
        }
    }
}
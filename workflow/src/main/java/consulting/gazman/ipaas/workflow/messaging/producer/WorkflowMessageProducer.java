package consulting.gazman.ipaas.workflow.messaging.producer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import consulting.gazman.ipaas.workflow.messaging.constants.QueueNames;
import consulting.gazman.ipaas.workflow.model.WorkflowStep;
import jakarta.annotation.PostConstruct;

import java.util.UUID;

@Component
public class WorkflowMessageProducer {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowMessageProducer.class);

    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE_NAME = "workflow.exchange";

    public WorkflowMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostConstruct
    public void init() {
        // Set up publisher confirms
        this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                logger.info("Message confirmed by broker: {}", correlationData != null ? correlationData.getId() : "");
            } else {
                logger.error("Message not confirmed by broker. Cause: {}", cause);
            }
        });
    }

    public void sendWorkflowStartMessage(String workflowName, UUID workflowId) {
        String routingKey = QueueNames.getWorkflowStartQueue(workflowName);
        convertAndSendMessage(routingKey, workflowId);
    }

    public void sendStepStartMessage(String stepName, UUID workflowId) {
        String routingKey = QueueNames.getStepStartQueue(stepName);
        convertAndSendMessage(routingKey, workflowId);
    }

    public void sendToDLQwithTTL(String stepName,UUID workflowId,long ttl){
        String routingKey = QueueNames.getStepDLQ(stepName);
        convertAndSendMessage(routingKey, workflowId, ttl);
        
    }

    public void sendStepEndMessage(String stepName, UUID workflowId) {
        String routingKey = QueueNames.getStepEndQueue(stepName);
        convertAndSendMessage(routingKey,workflowId);

        
    };

    protected void convertAndSendMessage(String routingKey,UUID workflowId){
        String correlationId = UUID.randomUUID().toString();
        CorrelationData correlationData = new CorrelationData(correlationId);
        try {
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, workflowId, correlationData);
            logger.info("sending message. WorkflowId: {}, CorrelationId: {}, RoutingKey: {}",
             workflowId, correlationId, routingKey);
        } catch (Exception e) {
            logger.error("Failed to send message.  WorkflowId: {}, RoutingKey: {}",
             workflowId, routingKey, e);
            // You might want to rethrow the exception or handle it according to your application's needs
        }
    }

    protected void convertAndSendMessage(String routingKey, UUID workflowId, long ttl) {
        String correlationId = UUID.randomUUID().toString();
        CorrelationData correlationData = new CorrelationData(correlationId);
        
        try {
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, workflowId, message -> {
                message.getMessageProperties().setExpiration(String.valueOf(ttl));
                return message;
            }, correlationData);
    
            logger.info("Sending message. WorkflowId: {}, CorrelationId: {}, RoutingKey: {}, TTL: {}",
                        workflowId, correlationId, routingKey, ttl);
        } catch (Exception e) {
            logger.error("Failed to send message. WorkflowId: {}, RoutingKey: {}, TTL: {}",
                         workflowId, routingKey, ttl, e);
            // You might want to rethrow the exception or handle it according to your application's needs
        }
    }

    public void sendWorkflowRunningMessage(UUID workflowId,String workflowName) {
        String routingKey = QueueNames.getWorkflowStartQueue(workflowName);
        convertAndSendMessage(routingKey, workflowId);
    }


    
}
package consulting.gazman.ipaas.workflow.messaging.producer;


import consulting.gazman.ipaas.workflow.messaging.model.WorkflowMessage;
import consulting.gazman.ipaas.workflow.model.Workflow;
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
public class WorkflowMessageProducer{
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

    public void sendWorkflowEvent(Workflow workflow) {

        WorkflowMessage workflowMessage = new WorkflowMessage();
        workflowMessage.setWorkflowId(workflow.getId());
        workflowMessage.setWorkflowName(workflow.getName());
        workflowMessage.setAction("");
        convertAndSendMessage(QueueNames.getWorkflowQueue(workflowMessage.getWorkflowName()), workflowMessage);
    }

    public void sendStepEvent(WorkflowMessage message){

        convertAndSendMessage(String.valueOf(message.getStepChannel()), message);

    }

//    public void sendStepStartMessage(String stepName, UUID workflowId) {
//        String routingKey = QueueNames.getStepStartQueue(stepName);
//        convertAndSendMessage(routingKey, workflowId);
//    }

    public void sendToDLQwithTTL(String stepName,UUID workflowId,long ttl){
        String routingKey = QueueNames.getStepDLQ(stepName);
        convertAndSendMessage(routingKey, workflowId, ttl);
        
    }

    public void sendToRetryQueue(String stepName,UUID stepId,long ttl){
        String routingKey = QueueNames.getStepRetryQueue(stepName);
        convertAndSendMessage(routingKey, stepId, ttl);
        
    }



    protected void convertAndSendMessage(String routingKey,WorkflowMessage workflowMessage){
        String correlationId = UUID.randomUUID().toString();
        CorrelationData correlationData = new CorrelationData(correlationId);
        try {
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, workflowMessage, correlationData);
            logger.info("sending message. WorkflowId: {}, CorrelationId: {}, RoutingKey: {}",
                    workflowMessage, correlationId, routingKey);
        } catch (Exception e) {
            logger.error("Failed to send message.  WorkflowId: {}, RoutingKey: {}",
                    workflowMessage, routingKey, e);
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




    
}
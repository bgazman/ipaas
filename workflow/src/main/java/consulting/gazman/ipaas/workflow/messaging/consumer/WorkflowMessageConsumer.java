package consulting.gazman.ipaas.workflow.messaging.consumer;

import consulting.gazman.ipaas.workflow.messaging.model.WorkflowMessage;
import consulting.gazman.ipaas.workflow.orchestrator.WorkflowOrchestrator;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.Map;

public abstract class WorkflowMessageConsumer  {

    @Autowired
    protected final WorkflowOrchestrator workflowOrchestrator;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final ObjectMapper objectMapper;
    protected WorkflowMessageConsumer( WorkflowOrchestrator workflowOrchestrator,ObjectMapper objectMapper) {
        this.workflowOrchestrator = workflowOrchestrator;
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

  protected String getQueueName(){
    return "";
  }
    protected void processMessage(WorkflowMessage message) {
        workflowOrchestrator.handleWorkflowEvent(message);
    }

    protected void receiveMessage(Message message) {
        logger.info("Received message in queue {}", getQueueName());
        try {
//            Object payload = messageConverter.fromMessage(message);
            WorkflowMessage workflowMessage = convertMessage(message);
            processMessage(workflowMessage);
            

        }catch(Exception e){


        }
    }
}
package consulting.gazman.ipaas.workflow.config;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import consulting.gazman.ipaas.workflow.messaging.constants.QueueNames;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueInitializer {

private static final Logger logger = LoggerFactory.getLogger(QueueInitializer.class);
    private final AmqpAdmin amqpAdmin;

    @Autowired
    public QueueInitializer(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;


    }
    @PostConstruct
    public void initializeQueues() {
        logger.info("Starting queue initialization");

        TopicExchange exchange = new TopicExchange(QueueNames.WORKFLOW_EXCHANGE);
        amqpAdmin.declareExchange(exchange);
        logger.info("Declared exchange: " + QueueNames.WORKFLOW_EXCHANGE);

        // Initialize workflow queues
        for (String workflow : QueueNames.ALL_WORKFLOWS) {
            createWorkflowQueueGroup(exchange, workflow);

        }

        // Initialize step queues
        for (String step : QueueNames.ALL_STEPS) {
            createStepQueueGroup(exchange, step);

        }

        logger.info("[QUEUE-INIT] Queue initialization completed");
    }

    private void createWorkflowQueueGroup(TopicExchange exchange, String workflowName) {
        logger.info("Creating queue group for workflow: " + workflowName);

        String startQueueName = QueueNames.getWorkflowStartQueue(workflowName);
        String endQueueName = QueueNames.getWorkflowEndQueue(workflowName);
        String dlqName = QueueNames.getWorkflowDLQ(workflowName);

        Queue startQueue = createQueueWithDLQ(startQueueName, dlqName);
        Queue endQueue = createQueue(endQueueName);
        Queue dlq = createDLQ(dlqName, startQueueName);

        amqpAdmin.declareQueue(startQueue);
        amqpAdmin.declareQueue(endQueue);
        amqpAdmin.declareQueue(dlq);

        amqpAdmin.declareBinding(BindingBuilder.bind(startQueue).to(exchange).with(startQueueName));
        amqpAdmin.declareBinding(BindingBuilder.bind(endQueue).to(exchange).with(endQueueName));
        logger.info("Binding start queue with routing key: {}", QueueNames.QUEUE_PREFIX + workflowName);
        logger.info("Created and bound queues for workflow: " + workflowName);
    }

    private void createStepQueueGroup(TopicExchange exchange, String stepName) {
        logger.info("Creating queue group for step: " + stepName);

        String startQueueName = QueueNames.getStepStartQueue(stepName);
        String endQueueName = QueueNames.getStepEndQueue(stepName);
        String dlqName = QueueNames.getStepDLQ(stepName);

        Queue startQueue = createQueueWithDLQ(startQueueName, dlqName);
        Queue endQueue = createQueue(endQueueName);
        Queue dlq = createDLQ(dlqName, startQueueName);

        amqpAdmin.declareQueue(startQueue);
        amqpAdmin.declareQueue(endQueue);
        amqpAdmin.declareQueue(dlq);

        amqpAdmin.declareBinding(BindingBuilder.bind(startQueue).to(exchange).with(startQueueName));
        amqpAdmin.declareBinding(BindingBuilder.bind(endQueue).to(exchange).with(endQueueName));

        logger.info("Created and bound queues for step: " + stepName);
    }

    private Queue createQueueWithDLQ(String queueName, String dlqName) {
        logger.debug("Creating queue with DLQ: " + queueName + " (DLQ: " + dlqName + ")");
        return QueueBuilder.durable(queueName)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", dlqName)
                .build();
    }

    private Queue createQueue(String queueName) {
        logger.debug("Creating queue: " + queueName);
        return QueueBuilder.durable(queueName).build();
    }

    private Queue createDLQ(String dlqName, String targetQueueName) {
        logger.debug("Creating DLQ: " + dlqName + " (Target: " + targetQueueName + ")");
        return QueueBuilder.durable(dlqName)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", targetQueueName)
                .build();
    }

    @PostConstruct
public void verifyQueues() {
    QueueNames.ALL_WORKFLOWS.forEach(workflow -> {
        String queueName = QueueNames.getWorkflowStartQueue(workflow);
        logger.info("Verifying queue: " + queueName);
        amqpAdmin.getQueueProperties(queueName);
    });

    QueueNames.ALL_STEPS.forEach(step -> {
        String queueName = QueueNames.getStepStartQueue(step);
        logger.info("Verifying queue: " + queueName);
        amqpAdmin.getQueueProperties(queueName);
    });
}
}
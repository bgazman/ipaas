package consulting.gazman.ipaas.workflow.messaging.constants;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;
public final class QueueNames {
    private QueueNames() {}

    public static final String WORKFLOW_EXCHANGE = "workflow.exchange";

    public static final String QUEUE_PREFIX = "core.";
    public static final String START_SUFFIX = ".start";
    public static final String END_SUFFIX = ".end";
    public static final String DLQ_SUFFIX = ".dlq";

    // Workflow names
    public static final String WORKFLOW_SUBMIT_ORDER = "submit-order";


    // Step names
    public static final String STEP_VALIDATE_ORDER = "validate-order";


    public static final List<String> ALL_WORKFLOWS = List.of(
        WORKFLOW_SUBMIT_ORDER

    );

    public static final List<String> ALL_STEPS = List.of(
        STEP_VALIDATE_ORDER
    );

    public static boolean isValidWorkflow(String queueName) {
        return ALL_WORKFLOWS.contains(queueName);
    }
    public static boolean isValidStep(String queueName) {
        return ALL_STEPS.contains(queueName);
    }

    public static String getWorkflowStartQueue(String workflowName) {
        return QUEUE_PREFIX + "workflow." + workflowName + START_SUFFIX;
    }

    public static String getWorkflowEndQueue(String workflowName) {
        return QUEUE_PREFIX + "workflow." + workflowName + END_SUFFIX;
    }

    public static String getWorkflowDLQ(String workflowName) {
        return QUEUE_PREFIX + "workflow." + workflowName + DLQ_SUFFIX;
    }

    public static String getStepStartQueue(String stepName) {
        return QUEUE_PREFIX + "step." + stepName + START_SUFFIX;
    }

    public static String getStepEndQueue(String stepName) {
        return QUEUE_PREFIX + "step." + stepName + END_SUFFIX;
    }

    public static String getStepDLQ(String stepName) {
        return QUEUE_PREFIX + "step." + stepName + DLQ_SUFFIX;
    }
}
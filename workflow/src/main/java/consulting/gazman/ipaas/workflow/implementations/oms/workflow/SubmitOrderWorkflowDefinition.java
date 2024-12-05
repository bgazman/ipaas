package consulting.gazman.ipaas.workflow.implementations.oms.workflow;

import java.util.List;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import consulting.gazman.ipaas.workflow.messaging.constants.QueueNames;
import consulting.gazman.ipaas.workflow.model.Workflow;
import consulting.gazman.ipaas.workflow.model.WorkflowDefinition;
import consulting.gazman.ipaas.workflow.model.WorkflowStep;

import java.util.UUID;

public class SubmitOrderWorkflowDefinition implements WorkflowDefinition {

    private final List<WorkflowStep> steps = new ArrayList<>();
    private final static String WORKFLOW_NAME = "submit-order";

    @Override
    public void initialize(Workflow workflow) {
        workflow.setName(WORKFLOW_NAME);
        workflow.setStatus("Initialized");
        workflow.setCreatedAt(LocalDateTime.now());
        workflow.setUpdatedAt(LocalDateTime.now());
    }

    @Override
    public List<WorkflowStep> defineSteps(Workflow workflow) {
        addStep(workflow, QueueNames.STEP_VALIDATE_ORDER, 1,3);
        return steps;
    }

    @Override
    public List<List<WorkflowStep>> defineParallelSteps(Workflow workflow) {
        // Define parallel steps if any
        return new ArrayList<>();
    }

    @Override
    public int getMaxRetries() {
        return 3;
    }

    @Override
    public Duration getStepTimeout() {
        return Duration.ofMinutes(5);
    }

    @Override
    public Duration getWorkflowTimeout() {
        return Duration.ofHours(1);
    }
    @Override
    public void addStep(Workflow workflow, String stepName, int order,int maxRetries) {
        WorkflowStep step = new WorkflowStep();
        step.setId(UUID.randomUUID());
        step.setWorkflow(workflow);
        step.setStepName(stepName);
        step.setStatus("PENDING");
        step.setStepOrder(order);
        step.setRetryCount(0);
        step.setMaxRetries(maxRetries);
        steps.add(step);
    }
}

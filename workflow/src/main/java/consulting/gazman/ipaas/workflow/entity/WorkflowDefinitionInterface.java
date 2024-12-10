package consulting.gazman.ipaas.workflow.entity;

import java.util.List;
import java.time.Duration;


public interface WorkflowDefinitionInterface {
    void initialize(Workflow workflow);
    List<WorkflowStep> defineSteps(Workflow workflow);
    List<List<WorkflowStep>> defineParallelSteps(Workflow workflow);
    int getMaxRetries();
    Duration getStepTimeout();
    Duration getWorkflowTimeout();
    void addStep(Workflow workflow, String stepName, int order,int retryCount);
}

package consulting.gazman.ipaas.workflow.model;

import java.util.List;
import java.time.Duration;


public interface WorkflowDefinition {
    void initialize(Workflow workflow);
    List<WorkflowStep> defineSteps(Workflow workflow);
    List<List<WorkflowStep>> defineParallelSteps(Workflow workflow);
    int getMaxRetries();
    Duration getStepTimeout();
    Duration getWorkflowTimeout();
    void addStep(Workflow workflow, String stepName, int order,int retryCount);
}

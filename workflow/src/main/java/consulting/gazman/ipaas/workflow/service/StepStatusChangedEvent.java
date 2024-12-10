package consulting.gazman.ipaas.workflow.service;

import org.springframework.context.ApplicationEvent;

import consulting.gazman.ipaas.workflow.entity.WorkflowStep;

public class StepStatusChangedEvent extends ApplicationEvent {
    private final WorkflowStep step;
    private final String oldStatus;
    private final String newStatus;

    public StepStatusChangedEvent(Object source, WorkflowStep step, String oldStatus, String newStatus) {
        super(source);
        this.step = step;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }

    // Getters...
}
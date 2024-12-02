package consulting.gazman.ipaas.workflow.api.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

public class WorkflowSummary {
    private UUID id;
    public WorkflowSummary() {
    }
    private LocalDateTime completedAt;
    private Long duration;  // in milliseconds
    private String currentStep;  // name of current executing step
    private List<WorkflowStepSummary> steps;  // Add this
    public List<WorkflowStepSummary> getSteps() {
        return steps;
    }
    public void setSteps(List<WorkflowStepSummary> steps) {
        this.steps = steps;
    }
    private String name;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
    public Long getDuration() {
        return duration;
    }
    public void setDuration(Long duration) {
        this.duration = duration;
    }
    public String getCurrentStep() {
        return currentStep;
    }
    public void setCurrentStep(String currentStep) {
        this.currentStep = currentStep;
    }


    // constructors, getters, setters
}
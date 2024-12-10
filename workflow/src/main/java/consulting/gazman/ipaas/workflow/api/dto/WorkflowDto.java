package consulting.gazman.ipaas.workflow.api.dto;

import java.util.UUID;

public class WorkflowDto {
    private UUID id;
    private String name;
    private String correlationId;
    private String status;
    private String type;
    private int maxRetries;
    private int retryCount;
    private String slaDeadline;
    private String createdAt;
    private String updatedAt;
    private String completedAt;
    private String createdBy;
    private String updatedBy;
    private int priority;
    private UUID parentWorkflowId;

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

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public String getSlaDeadline() {
        return slaDeadline;
    }

    public void setSlaDeadline(String slaDeadline) {
        this.slaDeadline = slaDeadline;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public UUID getParentWorkflowId() {
        return parentWorkflowId;
    }

    public void setParentWorkflowId(UUID parentWorkflowId) {
        this.parentWorkflowId = parentWorkflowId;
    }

// Getters and setters
}

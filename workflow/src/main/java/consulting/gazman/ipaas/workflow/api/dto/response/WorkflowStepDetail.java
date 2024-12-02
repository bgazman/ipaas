package consulting.gazman.ipaas.workflow.api.dto.response;

import java.util.UUID;
import java.time.LocalDateTime;

public class WorkflowStepDetail {
    private UUID id;
    private String name;
    private String status;
    private Integer order;
    private Integer retryCount;
    private Integer maxRetries;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    // Default constructor
    public WorkflowStepDetail() {}

    // All-args constructor if needed
    public WorkflowStepDetail(UUID id, String name, String status, Integer order,
                              Integer retryCount, Integer maxRetries,
                              LocalDateTime startedAt, LocalDateTime completedAt) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.order = order;
        this.retryCount = retryCount;
        this.maxRetries = maxRetries;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
    }

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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public Integer getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(Integer maxRetries) {
        this.maxRetries = maxRetries;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    // Getters and setters

}
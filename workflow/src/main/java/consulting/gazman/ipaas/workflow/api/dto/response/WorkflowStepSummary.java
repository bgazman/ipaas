package consulting.gazman.ipaas.workflow.api.dto.response;
import java.util.UUID;

public class WorkflowStepSummary {

    private UUID id;
    private String name;
    private String status;
    private Integer order;
    private Integer retryCount;
    private Integer maxRetries;

    public WorkflowStepSummary() {
    }

    public WorkflowStepSummary(UUID id, String name, String status, Integer order, Integer retryCount,
                               Integer maxRetries) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.order = order;
        this.retryCount = retryCount;
        this.maxRetries = maxRetries;
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
    // constructors, getters, setters
}
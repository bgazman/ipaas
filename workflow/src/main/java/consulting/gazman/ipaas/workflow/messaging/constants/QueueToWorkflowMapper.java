package consulting.gazman.ipaas.workflow.messaging.constants;
import jakarta.annotation.PostConstruct;

import java.util.Map;
import java.util.HashMap;
import org.springframework.stereotype.Component;

@Component
public class QueueToWorkflowMapper {

    // This can be populated dynamically from configuration files, a database, or annotations
    private final Map<String, String> workflowQueueMapping = new HashMap<>();

    @PostConstruct
    public void init() {
        // Load the mappings during application initialization
        workflowQueueMapping.put("SubmitOrder", QueueNames.getWorkflowStartQueue(QueueNames.WORKFLOW_SUBMIT_ORDER));
        // Add other workflows dynamically from a source
    }

    // Dynamically resolve queue name based on workflow name
    public String getQueueNameForWorkflow(String workflowName) {
        String queueName = workflowQueueMapping.get(workflowName);
        if (queueName == null) {
            throw new IllegalArgumentException("Queue name not found for workflow: " + workflowName);
        }
        return queueName;
    }
}

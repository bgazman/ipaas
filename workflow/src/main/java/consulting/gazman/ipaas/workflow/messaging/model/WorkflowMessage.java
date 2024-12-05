package consulting.gazman.ipaas.workflow.messaging.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkflowMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String workflowName;
    private UUID workflowId;
    private String stepName;
    private String stepChannel;
    private UUID stepId;
    private String action;
    private String data;
}
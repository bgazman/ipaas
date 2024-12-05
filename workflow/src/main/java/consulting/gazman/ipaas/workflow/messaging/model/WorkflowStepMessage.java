package consulting.gazman.ipaas.workflow.messaging.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkflowStepMessage<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID workflowId;
    private UUID stepId;
    private String action;
    private T payload;
}
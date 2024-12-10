package consulting.gazman.ipaas.workflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import consulting.gazman.ipaas.workflow.entity.WorkflowPayload;

import java.util.UUID;
@Repository
public interface WorkflowPayloadRepository extends JpaRepository<WorkflowPayload, UUID> {
    WorkflowPayload findByWorkflowId(UUID workflowId);
}
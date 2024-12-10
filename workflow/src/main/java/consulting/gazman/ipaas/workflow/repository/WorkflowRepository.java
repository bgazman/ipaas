package consulting.gazman.ipaas.workflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import consulting.gazman.ipaas.workflow.entity.Workflow;

import java.util.List;
import java.util.UUID;



import org.springframework.data.jpa.repository.Query;

@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, UUID> {

    // Find workflows by name
    List<Workflow> findByName(String name);

    // Find workflows by status
    List<Workflow> findByStatus(String status);

    // Find workflows by name and status
    List<Workflow> findByNameAndStatus(String name, String status);

    // Find workflows by parentWorkflowId (for sub-workflows)
    List<Workflow> findByParentWorkflowId(UUID parentWorkflowId);

    // Custom query for workflows close to their SLA deadline
    @Query("SELECT w FROM Workflow w WHERE w.slaDeadline < CURRENT_TIMESTAMP")
    List<Workflow> findWorkflowsCloseToDeadline();
}




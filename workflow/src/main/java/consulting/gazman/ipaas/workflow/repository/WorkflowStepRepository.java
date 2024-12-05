package consulting.gazman.ipaas.workflow.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import consulting.gazman.ipaas.workflow.model.StepStatusHistory;
import consulting.gazman.ipaas.workflow.model.Workflow;
import consulting.gazman.ipaas.workflow.model.WorkflowStep;

import java.util.UUID;
import java.util.List;

@Repository
public interface WorkflowStepRepository extends JpaRepository<WorkflowStep, UUID> {

    WorkflowStep findFirstByWorkflowAndStatusOrderByStepOrderAsc(Workflow workflow, String status);
    List<StepStatusHistory> findByWorkflowSteps_Id(UUID stepId);
    Optional<WorkflowStep> findByIdAndWorkflowId(UUID id, UUID workflowId);
    List<WorkflowStep> findByWorkflowId(UUID workflowId);
    WorkflowStep findFirstByWorkflowIdOrderByStepOrderAsc(UUID workflowId);

}


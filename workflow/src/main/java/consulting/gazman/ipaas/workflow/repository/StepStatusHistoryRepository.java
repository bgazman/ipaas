package consulting.gazman.ipaas.workflow.repository;

import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import consulting.gazman.ipaas.workflow.entity.StepStatusHistory;

@Repository
public interface StepStatusHistoryRepository extends JpaRepository<StepStatusHistory, UUID> {
    List<StepStatusHistory> findByStep_Id(UUID stepId);
    List<StepStatusHistory> findByStep_IdOrderByChangedAtDesc(UUID stepId);
}
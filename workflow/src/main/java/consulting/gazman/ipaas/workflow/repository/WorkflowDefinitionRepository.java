package consulting.gazman.ipaas.workflow.repository;



import consulting.gazman.ipaas.workflow.entity.WorkflowDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkflowDefinitionRepository extends JpaRepository<WorkflowDefinition, UUID> {
    List<WorkflowDefinition> findByDomain(String domain);

    Optional<WorkflowDefinition> findByDomainAndName(String domain, String name);
}

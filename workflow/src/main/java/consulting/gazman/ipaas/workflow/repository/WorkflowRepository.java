package consulting.gazman.ipaas.workflow.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import consulting.gazman.ipaas.workflow.model.Workflow;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, UUID> {

    Page<Workflow> findByStatusAndNameContaining(String status, String name, Pageable pageable);
    List<Workflow> findByStatus(String status);

    Page<Workflow> findByStatus(String status, Pageable pageable);

    Page<Workflow> findByNameContaining(String name, Pageable pageable);

    @EntityGraph(attributePaths = "steps")
    Page<Workflow> findAll(Specification<Workflow> spec, Pageable pageable);

//    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
//            attributePaths = {"steps", "payload"})
//    Optional<Workflow> findById(UUID id);




}
package consulting.gazman.ipaas.workflow.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import consulting.gazman.ipaas.workflow.api.dto.request.WorkflowSubmitRequest;
import consulting.gazman.ipaas.workflow.api.dto.response.StepHistoryResponse;
import consulting.gazman.ipaas.workflow.api.dto.response.StepStatusHistoryEntry;
import consulting.gazman.ipaas.workflow.api.dto.response.WorkflowDetailsResponse;
import consulting.gazman.ipaas.workflow.api.dto.response.WorkflowListResponse;
import consulting.gazman.ipaas.workflow.api.dto.response.WorkflowSummary;
import consulting.gazman.ipaas.workflow.api.dto.response.WorkflowResubmitResponse;
import consulting.gazman.ipaas.workflow.api.dto.response.WorkflowStepDetail;
import consulting.gazman.ipaas.workflow.api.dto.response.WorkflowStepSummary;
import consulting.gazman.ipaas.workflow.api.dto.response.WorkflowSubmitResponse;
import consulting.gazman.ipaas.workflow.api.exception.ApiException;
import consulting.gazman.ipaas.workflow.api.exception.InvalidWorkflowNameException;
import consulting.gazman.ipaas.workflow.api.exception.WorkflowCreationException;
import consulting.gazman.ipaas.workflow.api.exception.WorkflowNotFoundException;
import consulting.gazman.ipaas.workflow.enums.WorkflowStatus;
import consulting.gazman.ipaas.workflow.messaging.constants.QueueNames;
import consulting.gazman.ipaas.workflow.enums.WorkflowType;
import consulting.gazman.ipaas.workflow.messaging.model.WorkflowMessage;
import consulting.gazman.ipaas.workflow.messaging.producer.WorkflowMessageProducer;
import consulting.gazman.ipaas.workflow.model.StepStatusHistory;
import consulting.gazman.ipaas.workflow.model.Workflow;
import consulting.gazman.ipaas.workflow.model.WorkflowPayload;
import consulting.gazman.ipaas.workflow.model.WorkflowStep;
import consulting.gazman.ipaas.workflow.repository.StepStatusHistoryRepository;
import consulting.gazman.ipaas.workflow.repository.WorkflowRepository;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkflowServiceImpl<T> implements WorkflowService {

    private final WorkflowRepository workflowRepository;

    private final WorkflowMessageProducer messageProducer;
    private final ObjectMapper objectMapper;
    private StepStatusHistoryRepository stepStatusHistoryRepository;
    public WorkflowServiceImpl(WorkflowRepository workflowRepository,
                               WorkflowMessageProducer messageProducer, ObjectMapper objectMapper) {
        this.workflowRepository = workflowRepository;
        this.messageProducer = messageProducer;
        this.objectMapper = objectMapper;
    }



    @Override
    public WorkflowResubmitResponse resubmitWorkflow(UUID workflowId) {
        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new WorkflowNotFoundException("Workflow not found: " + workflowId));

        workflow.setStatus(WorkflowStatus.SUBMITTED.name());
        workflow.setUpdatedAt(LocalDateTime.now());
        workflow.setUpdatedBy("system"); // You might want to get this from authentication context
        workflow = workflowRepository.save(workflow);
        // Assuming T is String in this case

        messageProducer.sendWorkflowEvent(workflow);

        workflow = workflowRepository.save(workflow);

        return mapToWorkflowResubmitResponse(workflow);
    }




    @Override
    public WorkflowSubmitResponse submitWorkflow(WorkflowSubmitRequest request) throws ApiException {
        if (!QueueNames.isValidWorkflow(request.getWorkflowName())) {
            throw new InvalidWorkflowNameException("Invalid workflow name: " + request.getWorkflowName());
        }

        String workflowName = request.getWorkflowName();

        Workflow workflow = new Workflow();
        workflow.setName(workflowName);
        workflow.setType(request.getWorkflowType());
        workflow.setStatus(WorkflowStatus.SUBMITTED.name());
        workflow.setCreatedAt(LocalDateTime.now());
        workflow.setUpdatedAt(LocalDateTime.now());

        WorkflowPayload workflowPayload = new WorkflowPayload();
        workflowPayload.setWorkflow(workflow);
        workflowPayload.setPayload(convertPayloadToString(request.getPayload()));

        workflow.setPayload(workflowPayload);

        try {
            workflow = workflowRepository.save(workflow);
        } catch (Exception e) {
            throw new WorkflowCreationException("Failed to create workflow: " + request.getWorkflowName());
        }
        try {

            messageProducer.sendWorkflowEvent(workflow);
        } catch (Exception e) {
            // Consider whether you want to roll back the workflow creation if message sending fails
            throw new ApiException("Workflow created but failed to initiate execution: " + workflow.getId());
        }

        return mapToWorkflowSubmitResponse(workflow);
    }
    private String convertPayloadToString(Object payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch ( JsonProcessingException e) {
            throw new RuntimeException("Error converting payload to string", e);
        }
    }



    @Override
    public WorkflowListResponse getWorkflows(int page, int pageSize, String status,
                                             String workflowName, LocalDateTime fromDate, LocalDateTime toDate) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());

        // Create specification for filtering
        Specification<Workflow> spec = buildWorkflowSpecification(status, workflowName, fromDate, toDate);

        // Get page of workflows
        Page<Workflow> workflowPage = workflowRepository.findAll(spec, pageable);

        // Map to response
        List<WorkflowSummary> summaries = workflowPage.getContent().stream()
                .map(this::mapToSummary)
                .collect(Collectors.toList());

        return new WorkflowListResponse(
                summaries,
                workflowPage.getTotalPages(),
                workflowPage.getTotalElements(),
                workflowPage.getNumber()
        );
    }

    @Override
    public StepHistoryResponse getStepHistory(UUID workflowId, UUID stepId) {
        // Verify workflow exists
        WorkflowStep workflowStep = new WorkflowStep() ;

        // Get history entries
        List<StepStatusHistory> historyEntries = stepStatusHistoryRepository
                .findByStep_IdOrderByChangedAtDesc(stepId);

        // Map to response
        StepHistoryResponse response = new StepHistoryResponse();
        response.setStepId(stepId);
        response.setStepName(workflowStep.getStepName());
        response.setStatusHistory(historyEntries.stream()
                .map(this::mapToHistoryEntry)
                .collect(Collectors.toList()));

        return response;
    }

    private StepStatusHistoryEntry mapToHistoryEntry(StepStatusHistory history) {
        StepStatusHistoryEntry entry = new StepStatusHistoryEntry();
        entry.setOldStatus(history.getOldStatus());
        entry.setNewStatus(history.getNewStatus());
        entry.setReason(history.getReason());
        entry.setChangedAt(history.getChangedAt());
        entry.setChangedBy(history.getChangedBy());
        return entry;
    }

    private Specification<Workflow> buildWorkflowSpecification(String status,
                                                               String workflowName, LocalDateTime fromDate, LocalDateTime toDate) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (status != null && !status.isEmpty()) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (workflowName != null && !workflowName.isEmpty()) {
                predicates.add(cb.equal(root.get("name"), workflowName));
            }

            if (fromDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), fromDate));
            }

            if (toDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), toDate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private WorkflowSummary mapToSummary(Workflow workflow) {
        WorkflowSummary summary = new WorkflowSummary();
        summary.setId(workflow.getId());
        summary.setName(workflow.getName());
        summary.setStatus(workflow.getStatus());
        summary.setCreatedAt(workflow.getCreatedAt());
        summary.setUpdatedAt(workflow.getUpdatedAt());
        summary.setCompletedAt(workflow.getCompletedAt());

        if (workflow.getCompletedAt() != null && workflow.getCreatedAt() != null) {
            summary.setDuration(ChronoUnit.MILLIS.between(
                    workflow.getCreatedAt(), workflow.getCompletedAt()));
        }

        // Map steps
        if (workflow.getSteps() != null) {
            summary.setSteps(workflow.getSteps().stream()
                    .map(this::mapToStepSummary)
                    .collect(Collectors.toList()));

            // Set current step
            workflow.getSteps().stream()
                    .filter(step -> "EXECUTING".equals(step.getStatus()))
                    .findFirst()
                    .ifPresent(step -> summary.setCurrentStep(step.getStepName()));
        }

        return summary;
    }

    private WorkflowStepSummary mapToStepSummary(WorkflowStep step) {
        WorkflowStepSummary summary = new WorkflowStepSummary();
        summary.setId(step.getId());
        summary.setName(step.getStepName());
        summary.setStatus(step.getStatus());
        summary.setOrder(step.getStepOrder());
        summary.setRetryCount(step.getRetryCount());
        summary.setMaxRetries(step.getMaxRetries());
        return summary;
    }

    @Transactional(readOnly = true)
    @Override
    public WorkflowDetailsResponse getWorkflowDetails(UUID workflowId) {
        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new RuntimeException("Workflow not found"));

        return mapToWorkflowDetails(workflow);
    }

    public Workflow getWorkflowById(UUID workflowId) {
        return workflowRepository.findById(workflowId)
                .orElseThrow(() -> new WorkflowNotFoundException("Workflow not found"));
    }

    public void updateWorkflowStatus(UUID workflowId, WorkflowStatus status) {
        Workflow workflow = getWorkflowById(workflowId);
        workflow.setStatus(status.name());
        workflowRepository.save(workflow);
    }

    @Override
    public void triggerWorkflowStep(UUID workflowId, String stepName, Integer stepOrder) {
        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new WorkflowNotFoundException("Workflow not found: " + workflowId));

        List<WorkflowStep> steps;

    }

    //////// M A P P E R S ////////
    private WorkflowSubmitResponse mapToWorkflowSubmitResponse(Workflow workflow) {
        return new WorkflowSubmitResponse(
                workflow.getId(),
                workflow.getStatus()
        );
    }

    private WorkflowResubmitResponse mapToWorkflowResubmitResponse(Workflow workflow) {
        return new WorkflowResubmitResponse(
                workflow.getId(),
                workflow.getName(),
                workflow.getStatus(),
                workflow.getUpdatedAt()
        );
    }
    private WorkflowDetailsResponse mapToWorkflowDetails(Workflow workflow) {
        WorkflowDetailsResponse response = new WorkflowDetailsResponse();
        response.setId(workflow.getId());
        response.setName(workflow.getName());
        response.setType(workflow.getType());
        response.setStatus(workflow.getStatus());
        response.setCreatedAt(workflow.getCreatedAt());
        response.setUpdatedAt(workflow.getUpdatedAt());
        response.setCompletedAt(workflow.getCompletedAt());



        response.setPayload(workflow.getPayload() != null ? workflow.getPayload() : null);
        if (workflow.getSteps() != null) {
            List<WorkflowStepDetail> stepDetails = workflow.getSteps().stream()
                    .map(this::mapToStepDetail)
                    .collect(Collectors.toList());
            response.setSteps(stepDetails);
        }

        return response;
    }

    private WorkflowStepDetail mapToStepDetail(WorkflowStep step) {
        WorkflowStepDetail detail = new WorkflowStepDetail();
        detail.setId(step.getId());
        detail.setName(step.getStepName());
        detail.setStatus(step.getStatus());
        detail.setOrder(step.getStepOrder());
        detail.setRetryCount(step.getRetryCount());
        detail.setMaxRetries(step.getMaxRetries());
        detail.setStartedAt(step.getStartedAt());
        detail.setCompletedAt(step.getCompletedAt());
        return detail;
    }

    @Override
    public void processWorkflow(UUID workflowId, WorkflowType workflowType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processWorkflow'");
    }


}




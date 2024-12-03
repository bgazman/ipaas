package consulting.gazman.ipaas.workflow.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import consulting.gazman.ipaas.workflow.repository.WorkflowStepRepository;
import consulting.gazman.ipaas.workflow.model.StepStatusHistory;
import consulting.gazman.ipaas.workflow.model.WorkflowStep;
import consulting.gazman.ipaas.workflow.repository.StepStatusHistoryRepository;
import org.springframework.context.ApplicationEventPublisher;
import java.util.UUID;

import consulting.gazman.ipaas.workflow.exception.WorkflowStepNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class StepStatusService {

    private final WorkflowStepRepository stepRepository;
    private final StepStatusHistoryRepository historyRepository;
    private final ApplicationEventPublisher eventPublisher;

    public StepStatusService(WorkflowStepRepository stepRepository,
                             StepStatusHistoryRepository historyRepository,
                             ApplicationEventPublisher eventPublisher) {
        this.stepRepository = stepRepository;
        this.historyRepository = historyRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void updateStepStatus(UUID stepId, String newStatus, String reason) {
        WorkflowStep step = stepRepository.findById(stepId)
            .orElseThrow(() -> new WorkflowStepNotFoundException(stepId));

        String oldStatus = step.getStatus();
        step.setStatus(newStatus);

        StepStatusHistory history  = new StepStatusHistory();
        // history.initialize();

        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        history.setReason(reason);
        history.setChangedBy("System"); // You might want to get this from SecurityContext

        stepRepository.save(step);
        historyRepository.save(history);

        // eventPublisher.publishEvent(new StepStatusChangedEvent(this, step, oldStatus, newStatus));
    }
}
package consulting.gazman.ipaas.workflow.api.dto.response;

import java.util.List;


public class WorkflowListResponse {

    private List<WorkflowSummary> workflows;
    private int totalPages;
    private long totalElements;
    public List<WorkflowSummary> getWorkflows() {
        return workflows;
    }
    public void setWorkflows(List<WorkflowSummary> workflows) {
        this.workflows = workflows;
    }
    public int getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    public long getTotalElements() {
        return totalElements;
    }
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    private int currentPage;
    public WorkflowListResponse(List<WorkflowSummary> workflows, int totalPages, long totalElements, int currentPage) {
        this.workflows = workflows;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
    }

}




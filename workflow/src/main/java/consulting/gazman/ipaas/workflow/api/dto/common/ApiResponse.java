package consulting.gazman.ipaas.workflow.api.dto.common;

public class ApiResponse<T> {
    private T data;
    public ApiResponse(T data, String status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }
    private String status;
    private String message;
    // getters and setters
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
package consulting.gazman.ipaas.workflow.api.controller;


import consulting.gazman.ipaas.workflow.api.dto.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public abstract class BaseController {

    // Reusable method to create success responses
    protected <T> ApiResponse<T> successResponse(T data, String message) {
        return new ApiResponse<>(data, "SUCCESS", message);
    }

    // Reusable method to create error responses
    protected <T> ApiResponse<T> errorResponse(String message) {
        return new ApiResponse<>(null, "ERROR", message);
    }

    // Extract metadata from the request
    protected Map<String, Object> extractMetadata(HttpServletRequest request) {
        return (Map<String, Object>) request.getAttribute("metadata");
    }

    // Log metadata (can be reused in multiple controllers)
    protected void logMetadata(Map<String, Object> metadata) {
        if (metadata != null) {
            // Pseudo-code: Log metadata for debugging
//            log("Metadata: " + metadata.toString());
        }
    }
}

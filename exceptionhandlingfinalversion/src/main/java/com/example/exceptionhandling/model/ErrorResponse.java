package com.example.exceptionhandling.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Standard error response for the entire application.
 *
 * IMPORTANT:
 *
 * Never return Java exception objects directly to the client.
 *
 * Instead return this controlled structure.
 */

public class ErrorResponse {
	
	private boolean success;

    private LocalDateTime timestamp;

    private String requestId;

    private String errorCode;

    private String message;

    private int status;

    private String path;

    private String method;

    private UserIdentity user;

    private List<ErrorDetail> errors;

    public ErrorResponse() {
    }

    public ErrorResponse(
            boolean success,
            LocalDateTime timestamp,
            String requestId,
            String errorCode,
            String message,
            int status,
            String path,
            String method,
            UserIdentity user,
            List<ErrorDetail> errors
    ) {
        this.success = success;
        this.timestamp = timestamp;
        this.requestId = requestId;
        this.errorCode = errorCode;
        this.message = message;
        this.status = status;
        this.path = path;
        this.method = method;
        this.user = user;
        this.errors = errors;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public UserIdentity getUser() {
        return user;
    }

    public void setUser(UserIdentity user) {
        this.user = user;
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetail> errors) {
        this.errors = errors;
    }

}

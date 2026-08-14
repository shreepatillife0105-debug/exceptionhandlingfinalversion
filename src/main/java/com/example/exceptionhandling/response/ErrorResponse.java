package com.example.exceptionhandling.response;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {

	private boolean success;
	
    private LocalDateTime timestamp;
	
	private String message;
	
	private String errorCode;
	
	private int status;
	
	private String path;
	
	private Map<String, String> errors;

	public ErrorResponse(boolean success, String message,String errorCode, int status) {
		this.success = success;
		this.message = message;
		this.errorCode = errorCode;
		this.status = status;
	}
	
	public ErrorResponse(boolean success,String message, String errorCode,int status,Map<String, String> errors) {
        this.success = success;
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.errors = errors;
    }

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}
	
	public String getErrorCode() {
        return errorCode;
    }

	public int getStatus() {
		return status;
	}

	public Map<String, String> getErrors() {
        return errors;
    }
	
	
	
	
}

package com.example.exceptionhandling.exception;

import com.example.exceptionhandling.enums.ErrorCode;

public class BusinessException extends RuntimeException{
	
	private final ErrorCode errorCode;

	public BusinessException(ErrorCode errorCode,String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public ErrorCode getErrorCode() {
		return errorCode;
	}
	
}

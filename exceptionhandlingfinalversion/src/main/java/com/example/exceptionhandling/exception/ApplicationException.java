package com.example.exceptionhandling.exception;

/**
 * Base exception for application/business-level exceptions.
 *
 * Example:
 *
 * StudentNotFoundException
 * StudentAlreadyExistsException
 * OrderNotFoundException
 * RideAlreadyAcceptedException
 *
 * can all extend ApplicationException.
 *
 * This keeps the exception hierarchy clean.
 */

public class ApplicationException extends RuntimeException{

	 private final ErrorCode errorCode;

	    public ApplicationException(
	            ErrorCode errorCode,
	            String message
	    ) {
	        super(message);
	        this.errorCode = errorCode;
	    }

	    public ErrorCode getErrorCode() {
	        return errorCode;
	    }
	
}

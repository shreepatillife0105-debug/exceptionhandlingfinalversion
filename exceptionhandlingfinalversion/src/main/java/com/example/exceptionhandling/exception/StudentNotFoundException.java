package com.example.exceptionhandling.exception;

/**
 * Business exception.
 *
 * HTTP status is NOT defined here.
 *
 * Why?
 *
 * We don't want the service layer to depend directly
 * on HTTP concepts.
 *
 * The GlobalExceptionHandler decides the HTTP response.
 */

public class StudentNotFoundException extends ApplicationException{

	 public StudentNotFoundException(String message) {

	        super(
	                ErrorCode.STUDENT_NOT_FOUND,
	                message
	        );
	    }
	
}

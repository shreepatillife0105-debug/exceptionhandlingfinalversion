package com.example.exceptionhandling.exception;

/**
 * Thrown when a student already exists.
 */

public class StudentAlreadyExistsException extends ApplicationException{

	 public StudentAlreadyExistsException(
	            String message
	    ) {

	        super(
	                ErrorCode.STUDENT_ALREADY_EXISTS,
	                message
	        );
	    }
	
}

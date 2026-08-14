package com.example.exceptionhandling.exception;

import com.example.exceptionhandling.enums.ErrorCode;

public class StudentNotFoundException extends BusinessException{

	public StudentNotFoundException(String message) {
		super(
				ErrorCode.STUDENT_NOT_FOUND
				,message);
	}
	
	
//	Approach 1 — @ResponseStatus
//	@ResponseStatus(HttpStatus.NOT_FOUND)
//	public class StudentNotFoundException
//	        extends BusinessException {
	
}

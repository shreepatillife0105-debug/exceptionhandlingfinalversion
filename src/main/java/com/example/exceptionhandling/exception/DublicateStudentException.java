package com.example.exceptionhandling.exception;

import com.example.exceptionhandling.enums.ErrorCode;

public class DublicateStudentException extends BusinessException{

	public DublicateStudentException(String message) {
		super(
				ErrorCode.STUDENT_ALREADY_EXISTS
				,message);
	}
	
}

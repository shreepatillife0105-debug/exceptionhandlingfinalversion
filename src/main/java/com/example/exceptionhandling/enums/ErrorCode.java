package com.example.exceptionhandling.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

	STUDENT_NOT_FOUND(
            HttpStatus.NOT_FOUND
    ),

    STUDENT_ALREADY_EXISTS(
            HttpStatus.CONFLICT
    ),

    INVALID_STUDENT(
            HttpStatus.BAD_REQUEST
    ),

    VALIDATION_FAILED(
            HttpStatus.BAD_REQUEST
    ),

    INVALID_PARAMETER_TYPE(
            HttpStatus.BAD_REQUEST
    ),

    MISSING_REQUEST_PARAMETER(
            HttpStatus.BAD_REQUEST
    ),

    INVALID_REQUEST_BODY(
            HttpStatus.BAD_REQUEST
    ),

    METHOD_NOT_ALLOWED(
            HttpStatus.METHOD_NOT_ALLOWED
    ),

    UNSUPPORTED_MEDIA_TYPE(
            HttpStatus.UNSUPPORTED_MEDIA_TYPE
    ),

    STUDENT_OPERATION_FAILED(
            HttpStatus.INTERNAL_SERVER_ERROR
    ),
	
	INTERNAL_SERVER_ERROR(
	        HttpStatus.INTERNAL_SERVER_ERROR
	);

    private final HttpStatus status;

    ErrorCode(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
	
}

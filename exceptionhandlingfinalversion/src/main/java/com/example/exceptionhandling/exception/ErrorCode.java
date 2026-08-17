package com.example.exceptionhandling.exception;

import org.springframework.http.HttpStatus;

/**
 * Central list of application error codes.
 *
 * WHY:
 * - Frontend should not depend on Java exception class names.
 * - Error codes remain stable even if implementation changes.
 * - Makes logging and debugging easier.
 *
 * Example:
 *
 * StudentNotFoundException
 *          ↓
 * STUDENT_NOT_FOUND
 *          ↓
 * HTTP 404
 */

public enum ErrorCode {
	
	// =========================
    // Authentication / Security
    // =========================

    UNAUTHORIZED(
            HttpStatus.UNAUTHORIZED,
            "Authentication is required"
    ),

    TOKEN_EXPIRED(
            HttpStatus.UNAUTHORIZED,
            "Authentication token has expired"
    ),

    FORBIDDEN(
            HttpStatus.FORBIDDEN,
            "You do not have permission to perform this operation"
    ),

    // =========================
    // Validation / Request
    // =========================

    VALIDATION_FAILED(
            HttpStatus.BAD_REQUEST,
            "Request validation failed"
    ),

    INVALID_REQUEST(
            HttpStatus.BAD_REQUEST,
            "Invalid request"
    ),

    INVALID_JSON(
            HttpStatus.BAD_REQUEST,
            "Invalid JSON request"
    ),

    INVALID_PARAMETER(
            HttpStatus.BAD_REQUEST,
            "Invalid request parameter"
    ),

    METHOD_NOT_ALLOWED(
            HttpStatus.METHOD_NOT_ALLOWED,
            "HTTP method is not supported"
    ),

    MEDIA_TYPE_NOT_SUPPORTED(
            HttpStatus.UNSUPPORTED_MEDIA_TYPE,
            "Media type is not supported"
    ),

    // =========================
    // Business
    // =========================

    STUDENT_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "Student not found"
    ),

    STUDENT_ALREADY_EXISTS(
            HttpStatus.CONFLICT,
            "Student already exists"
    ),

    // =========================
    // Database
    // =========================

    DATA_INTEGRITY_VIOLATION(
            HttpStatus.CONFLICT,
            "Database constraint violation"
    ),

    DATABASE_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Database operation failed"
    ),

    // =========================
    // Generic
    // =========================

    INTERNAL_SERVER_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "An unexpected error occurred"
    );

    private final HttpStatus status;
    private final String defaultMessage;

    ErrorCode(
            HttpStatus status,
            String defaultMessage
    ) {
        this.status = status;
        this.defaultMessage = defaultMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

}

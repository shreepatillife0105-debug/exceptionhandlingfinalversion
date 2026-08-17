package com.example.exceptionhandling.handler;

import com.example.exceptionhandling.exception.ApplicationException;
import com.example.exceptionhandling.exception.ErrorCode;
import com.example.exceptionhandling.model.ErrorDetail;
import com.example.exceptionhandling.model.ErrorResponse;
import com.example.exceptionhandling.model.UserIdentity;
import com.example.exceptionhandling.util.RequestContextUtil;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.HttpMessageNotReadableException;

import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * GLOBAL exception handler.
 *
 * This class converts Java/Spring exceptions into
 * a standard API response.
 *
 * IMPORTANT:
 *
 * Don't put business logic here.
 *
 * This class should mainly:
 *
 * 1. Identify exception
 * 2. Log it
 * 3. Convert it to ErrorResponse
 * 4. Return proper HTTP status
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	 private static final Logger log =
	            LoggerFactory.getLogger(
	                    GlobalExceptionHandler.class
	            );

	    // =========================================================
	    // 1. OUR APPLICATION / BUSINESS EXCEPTIONS
	    // =========================================================

	    @ExceptionHandler(ApplicationException.class)
	    public ResponseEntity<ErrorResponse>
	    handleApplicationException(
	            ApplicationException ex,
	            HttpServletRequest request
	    ) {

	        ErrorCode errorCode =
	                ex.getErrorCode();

	        UserIdentity user =
	                RequestContextUtil.getCurrentUser();

	        String requestId =
	                RequestContextUtil.getRequestId(request);

	        // Business exceptions normally don't need
	        // ERROR-level stack traces.
	        log.warn(
	                "Business exception | requestId={} | userId={} | username={} | method={} | path={} | errorCode={} | message={}",
	                requestId,
	                user.getUserId(),
	                user.getUsername(),
	                request.getMethod(),
	                request.getRequestURI(),
	                errorCode.name(),
	                ex.getMessage()
	        );

	        ErrorResponse response =
	                buildResponse(
	                        errorCode,
	                        ex.getMessage(),
	                        request,
	                        requestId,
	                        user,
	                        null
	                );

	        return ResponseEntity
	                .status(errorCode.getStatus())
	                .body(response);
	    }

	    // =========================================================
	    // 2. VALIDATION
	    // =========================================================

	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<ErrorResponse>
	    handleValidationException(
	            MethodArgumentNotValidException ex,
	            HttpServletRequest request
	    ) {

	        List<ErrorDetail> errors =
	                new ArrayList<>();

	        ex.getBindingResult()
	                .getFieldErrors()
	                .forEach(error -> {

	                    errors.add(
	                            new ErrorDetail(
	                                    error.getField(),
	                                    error.getDefaultMessage()
	                            )
	                    );
	                });

	        UserIdentity user =
	                RequestContextUtil.getCurrentUser();

	        String requestId =
	                RequestContextUtil.getRequestId(request);

	        log.warn(
	                "Validation exception | requestId={} | userId={} | path={}",
	                requestId,
	                user.getUserId(),
	                request.getRequestURI()
	        );

	        ErrorResponse response =
	                buildResponse(
	                        ErrorCode.VALIDATION_FAILED,
	                        ErrorCode.VALIDATION_FAILED
	                                .getDefaultMessage(),
	                        request,
	                        requestId,
	                        user,
	                        errors
	                );

	        return ResponseEntity
	                .badRequest()
	                .body(response);
	    }

	    // =========================================================
	    // 3. INVALID JSON
	    // =========================================================

	    @ExceptionHandler(
	            HttpMessageNotReadableException.class
	    )
	    public ResponseEntity<ErrorResponse>
	    handleInvalidJson(
	            HttpMessageNotReadableException ex,
	            HttpServletRequest request
	    ) {

	        UserIdentity user =
	                RequestContextUtil.getCurrentUser();

	        String requestId =
	                RequestContextUtil.getRequestId(request);

	        log.warn(
	                "Invalid JSON | requestId={} | userId={} | path={}",
	                requestId,
	                user.getUserId(),
	                request.getRequestURI()
	        );

	        ErrorResponse response =
	                buildResponse(
	                        ErrorCode.INVALID_JSON,
	                        ErrorCode.INVALID_JSON
	                                .getDefaultMessage(),
	                        request,
	                        requestId,
	                        user,
	                        null
	                );

	        return ResponseEntity
	                .badRequest()
	                .body(response);
	    }

	    // =========================================================
	    // 4. INVALID PARAMETER TYPE
	    // =========================================================

	    @ExceptionHandler(
	            MethodArgumentTypeMismatchException.class
	    )
	    public ResponseEntity<ErrorResponse>
	    handleTypeMismatch(
	            MethodArgumentTypeMismatchException ex,
	            HttpServletRequest request
	    ) {

	        UserIdentity user =
	                RequestContextUtil.getCurrentUser();

	        String requestId =
	                RequestContextUtil.getRequestId(request);

	        String message =
	                "Invalid value for parameter: "
	                        + ex.getName();

	        log.warn(
	                "Parameter type mismatch | requestId={} | userId={} | parameter={}",
	                requestId,
	                user.getUserId(),
	                ex.getName()
	        );

	        ErrorResponse response =
	                buildResponse(
	                        ErrorCode.INVALID_PARAMETER,
	                        message,
	                        request,
	                        requestId,
	                        user,
	                        null
	                );

	        return ResponseEntity
	                .badRequest()
	                .body(response);
	    }

	    // =========================================================
	    // 5. MISSING REQUEST PARAMETER
	    // =========================================================

	    @ExceptionHandler(
	            MissingServletRequestParameterException.class
	    )
	    public ResponseEntity<ErrorResponse>
	    handleMissingParameter(
	            MissingServletRequestParameterException ex,
	            HttpServletRequest request
	    ) {

	        UserIdentity user =
	                RequestContextUtil.getCurrentUser();

	        String requestId =
	                RequestContextUtil.getRequestId(request);

	        String message =
	                "Required parameter is missing: "
	                        + ex.getParameterName();

	        ErrorResponse response =
	                buildResponse(
	                        ErrorCode.INVALID_REQUEST,
	                        message,
	                        request,
	                        requestId,
	                        user,
	                        null
	                );

	        return ResponseEntity
	                .badRequest()
	                .body(response);
	    }

	    // =========================================================
	    // 6. HTTP METHOD NOT SUPPORTED
	    // =========================================================

	    @ExceptionHandler(
	            HttpRequestMethodNotSupportedException.class
	    )
	    public ResponseEntity<ErrorResponse>
	    handleMethodNotSupported(
	            HttpRequestMethodNotSupportedException ex,
	            HttpServletRequest request
	    ) {

	        UserIdentity user =
	                RequestContextUtil.getCurrentUser();

	        String requestId =
	                RequestContextUtil.getRequestId(request);

	        ErrorResponse response =
	                buildResponse(
	                        ErrorCode.METHOD_NOT_ALLOWED,
	                        "HTTP method "
	                                + request.getMethod()
	                                + " is not supported",
	                        request,
	                        requestId,
	                        user,
	                        null
	                );

	        return ResponseEntity
	                .status(
	                        HttpStatus.METHOD_NOT_ALLOWED
	                )
	                .body(response);
	    }

	    // =========================================================
	    // 7. MEDIA TYPE
	    // =========================================================

	    @ExceptionHandler(
	            HttpMediaTypeNotSupportedException.class
	    )
	    public ResponseEntity<ErrorResponse>
	    handleMediaType(
	            HttpMediaTypeNotSupportedException ex,
	            HttpServletRequest request
	    ) {

	        UserIdentity user =
	                RequestContextUtil.getCurrentUser();

	        String requestId =
	                RequestContextUtil.getRequestId(request);

	        ErrorResponse response =
	                buildResponse(
	                        ErrorCode.MEDIA_TYPE_NOT_SUPPORTED,
	                        ErrorCode.MEDIA_TYPE_NOT_SUPPORTED
	                                .getDefaultMessage(),
	                        request,
	                        requestId,
	                        user,
	                        null
	                );

	        return ResponseEntity
	                .status(
	                        HttpStatus.UNSUPPORTED_MEDIA_TYPE
	                )
	                .body(response);
	    }

	    // =========================================================
	    // 8. DATABASE CONSTRAINT
	    // =========================================================

	    @ExceptionHandler(
	            DataIntegrityViolationException.class
	    )
	    public ResponseEntity<ErrorResponse>
	    handleDataIntegrityViolation(
	            DataIntegrityViolationException ex,
	            HttpServletRequest request
	    ) {

	        UserIdentity user =
	                RequestContextUtil.getCurrentUser();

	        String requestId =
	                RequestContextUtil.getRequestId(request);

	        /*
	         * Don't return the database's raw error message.
	         *
	         * It may expose:
	         * - table names
	         * - column names
	         * - SQL
	         * - internal database details
	         */

	        log.error(
	                "Database constraint violation | requestId={} | userId={} | path={}",
	                requestId,
	                user.getUserId(),
	                request.getRequestURI(),
	                ex
	        );

	        ErrorResponse response =
	                buildResponse(
	                        ErrorCode.DATA_INTEGRITY_VIOLATION,
	                        ErrorCode.DATA_INTEGRITY_VIOLATION
	                                .getDefaultMessage(),
	                        request,
	                        requestId,
	                        user,
	                        null
	                );

	        return ResponseEntity
	                .status(HttpStatus.CONFLICT)
	                .body(response);
	    }

	    // =========================================================
	    // 9. OTHER DATABASE EXCEPTIONS
	    // =========================================================

	    @ExceptionHandler(DataAccessException.class)
	    public ResponseEntity<ErrorResponse>
	    handleDatabaseException(
	            DataAccessException ex,
	            HttpServletRequest request
	    ) {

	        UserIdentity user =
	                RequestContextUtil.getCurrentUser();

	        String requestId =
	                RequestContextUtil.getRequestId(request);

	        log.error(
	                "Database exception | requestId={} | userId={} | path={}",
	                requestId,
	                user.getUserId(),
	                request.getRequestURI(),
	                ex
	        );

	        ErrorResponse response =
	                buildResponse(
	                        ErrorCode.DATABASE_ERROR,
	                        ErrorCode.DATABASE_ERROR
	                                .getDefaultMessage(),
	                        request,
	                        requestId,
	                        user,
	                        null
	                );

	        return ResponseEntity
	                .status(
	                        HttpStatus.INTERNAL_SERVER_ERROR
	                )
	                .body(response);
	    }

	    // =========================================================
	    // 10. FINAL UNKNOWN EXCEPTION
	    // =========================================================

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<ErrorResponse>
	    handleUnknownException(
	            Exception ex,
	            HttpServletRequest request
	    ) {

	        UserIdentity user =
	                RequestContextUtil.getCurrentUser();

	        String requestId =
	                RequestContextUtil.getRequestId(request);

	        /*
	         * ALWAYS log the complete exception internally.
	         *
	         * But DON'T send stack trace to client.
	         */
	        log.error(
	                "UNEXPECTED EXCEPTION | requestId={} | userId={} | username={} | method={} | path={}",
	                requestId,
	                user.getUserId(),
	                user.getUsername(),
	                request.getMethod(),
	                request.getRequestURI(),
	                ex
	        );

	        ErrorResponse response =
	                buildResponse(
	                        ErrorCode.INTERNAL_SERVER_ERROR,
	                        ErrorCode.INTERNAL_SERVER_ERROR
	                                .getDefaultMessage(),
	                        request,
	                        requestId,
	                        user,
	                        null
	                );

	        return ResponseEntity
	                .status(
	                        HttpStatus.INTERNAL_SERVER_ERROR
	                )
	                .body(response);
	    }

	    // =========================================================
	    // COMMON RESPONSE BUILDER
	    // =========================================================

	    private ErrorResponse buildResponse(
	            ErrorCode errorCode,
	            String message,
	            HttpServletRequest request,
	            String requestId,
	            UserIdentity user,
	            List<ErrorDetail> errors
	    ) {

	        return new ErrorResponse(
	                false,
	                LocalDateTime.now(),
	                requestId,
	                errorCode.name(),
	                message,
	                errorCode.getStatus().value(),
	                request.getRequestURI(),
	                request.getMethod(),
	                user,
	                errors
	        );
	    }
	
}

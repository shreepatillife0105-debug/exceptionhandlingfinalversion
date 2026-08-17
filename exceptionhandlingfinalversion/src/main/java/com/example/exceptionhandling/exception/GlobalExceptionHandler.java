package com.example.exceptionhandling.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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

import com.example.exceptionhandling.enums.ErrorCode;
import com.example.exceptionhandling.response.ErrorResponse;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	 private static final Logger log =
	            LoggerFactory.getLogger(
	                    GlobalExceptionHandler.class
	            );

//	 	@ExceptionHandler(StudentNotFoundException.class)
//	    public ResponseEntity<ErrorResponse> handleStudentNotFound(
//	            StudentNotFoundException ex) {
//
//	 		ErrorResponse response = new ErrorResponse(
//	 				false,
//	 				ex.getMessage(),
//	 				ex.getErrorCode().name(),
//	 				HttpStatus.NOT_FOUND.value()
//	 			);
//	 		
//	        return ResponseEntity
//	                .status(HttpStatus.NOT_FOUND)
//	                .body(response);
//	    }

//	    @ExceptionHandler(DublicateStudentException.class)
//	    public ResponseEntity<ErrorResponse> handleDuplicateStudent(
//	            DublicateStudentException ex) {
//	    	
//	    	ErrorResponse response = new ErrorResponse(
//	 				false,
//	 				ex.getMessage(),
//	 				ex.getErrorCode().name(),
//	 				HttpStatus.NOT_FOUND.value()
//	 			);
//
//	        return ResponseEntity
//	                .status(HttpStatus.CONFLICT)
//	                .body(response);
//	    }
	
	
//	@ExceptionHandler(BusinessException.class)
//	public ResponseEntity<ErrorResponse> handleBusinessException(
//	        BusinessException ex) {
//
//	    HttpStatus status;
//
//	    switch (ex.getErrorCode()) {
//
//	        case STUDENT_NOT_FOUND ->
//	                status = HttpStatus.NOT_FOUND;
//
//	        case STUDENT_ALREADY_EXISTS ->
//	                status = HttpStatus.CONFLICT;
//
//	        default ->
//	                status = HttpStatus.BAD_REQUEST;
//	    }
//
//	    ErrorResponse response =
//	            new ErrorResponse(
//	                    false,
//	                    ex.getMessage(),
//	                    ex.getErrorCode().name(),
//	                    status.value()
//	            );
//
//	    return ResponseEntity
//	            .status(status)
//	            .body(response);
//	}
	
//	Approach 3 — @ExceptionHandler
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(
	        BusinessException ex) {

	    HttpStatus status = ex.getErrorCode().getStatus() ;
	    

	    ErrorResponse response =
	            new ErrorResponse(
	                    false,
	                    ex.getMessage(),
	                    ex.getErrorCode().name(),
	                    status.value()
	            );

	    return ResponseEntity
	            .status(status)
	            .body(response);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex){
		
		Map<String, String> errors = new HashMap<String, String>();
		
		ex.getBindingResult()
			.getFieldErrors()
				.forEach(error ->
							errors.put(
									error.getField(), 
									error.getDefaultMessage())
						);
		
		ErrorResponse response = new ErrorResponse(
										false,
										"Validation Failed",
										ErrorCode.VALIDATION_FAILED.name(),
										HttpStatus.BAD_REQUEST.value(),
										errors);
		
		return ResponseEntity.badRequest().body(response);
		
	}
	
	
	@ExceptionHandler()
	public ResponseEntity<ErrorResponse> handleConstraintsValidation(ConstraintViolationException ex){
		
		Map<String, String> errors = new HashMap();
		ex.getConstraintViolations()
			.forEach(violation->{
				String field = violation.getPropertyPath().toString();
				String message = violation.getMessage();
				errors.put(field, message);
			});
		
		ErrorResponse response = new ErrorResponse(
									false,
										"Validation failed",
											ErrorCode.VALIDATION_FAILED.name(),
												HttpStatus.BAD_REQUEST.value(),
													errors);
		
		return ResponseEntity.badRequest().body(response);
													
		
	}
	
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex){
		 
		 String message =
			        "Parameter '" + ex.getName()
			        + "' must be of type "
			        + ex.getRequiredType().getSimpleName();
		 
		 ErrorResponse response = new ErrorResponse(
				 				false,
				 					message,
				 						ErrorCode.INVALID_PARAMETER_TYPE.name(),
				 							HttpStatus.BAD_REQUEST.value());
		 
		 return ResponseEntity.badRequest().body(response);
		
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorResponse> handleMissingParameter( MissingServletRequestParameterException ex) {

	    String message =
	            "Required parameter '"
	            + ex.getParameterName()
	            + "' is missing";

	    ErrorResponse response =
	            new ErrorResponse(
	                    false,
	                    message,
	                    ErrorCode.MISSING_REQUEST_PARAMETER.name(),
	                    HttpStatus.BAD_REQUEST.value()
	            );

	    return ResponseEntity
	            .badRequest()
	            .body(response);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleInvalidJson(HttpMessageNotReadableException ex) {

	    ErrorResponse response =
	            new ErrorResponse(
	                    false,
	                    "Invalid request body",
	                    "INVALID_REQUEST_BODY",
	                    HttpStatus.BAD_REQUEST.value()
	            );

	    return ResponseEntity
	            .badRequest()
	            .body(response);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse>handleMethodNotSupported( HttpRequestMethodNotSupportedException ex) {

	    String message =
	            "HTTP method '"
	            + ex.getMethod()
	            + "' is not supported for this endpoint";

	    ErrorResponse response =
	            new ErrorResponse(
	                    false,
	                    message,
	                    "METHOD_NOT_ALLOWED",
	                    HttpStatus.METHOD_NOT_ALLOWED.value()
	            );

	    return ResponseEntity
	            .status(HttpStatus.METHOD_NOT_ALLOWED)
	            .body(response);
	}
	
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<ErrorResponse>
	        handleUnsupportedMediaType(
	                HttpMediaTypeNotSupportedException ex) {

	    ErrorResponse response =
	            new ErrorResponse(
	                    false,
	                    "Content-Type is not supported",
	                    "UNSUPPORTED_MEDIA_TYPE",
	                    HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()
	            );

	    return ResponseEntity
	            .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	            .body(response);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex,HttpServletRequest request){
		

	    log.error(
	            "Unexpected exception occurred",
	            request.getRequestURI(),
	            ex
	    );
		
		ErrorResponse response =  new ErrorResponse(
                					false,
                						"Something went wrong",
                							ErrorCode.INTERNAL_SERVER_ERROR.name(),
                								HttpStatus.INTERNAL_SERVER_ERROR.value()
								);
		  return ResponseEntity
		            .status(HttpStatus.INTERNAL_SERVER_ERROR)
		            .body(response);
		
	}
	
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse>
	        handleDataIntegrityViolation(
	                DataIntegrityViolationException ex) {

	    ErrorResponse response =
	            new ErrorResponse(
	                    false,
	                    "Database constraint violation",
	                    "DATA_INTEGRITY_VIOLATION",
	                    HttpStatus.CONFLICT.value()
	            );

	    return ResponseEntity
	            .status(HttpStatus.CONFLICT)
	            .body(response);
	}
	
	@ExceptionHandler(StudentAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse>
	        handleStudentAlreadyExists(
	                StudentAlreadyExistsException ex) {

	    ErrorResponse response =
	            new ErrorResponse(
	                    false,
	                    ex.getMessage(),
	                    ErrorCode.STUDENT_ALREADY_EXISTS.name(),
	                    HttpStatus.CONFLICT.value()
	            );

	    return ResponseEntity
	            .status(HttpStatus.CONFLICT)
	            .body(response);
	}
	
	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<ErrorResponse>
	        handleDataAccessException(
	                DataAccessException ex,
	                HttpServletRequest request) {

	    log.error(
	            "Database access error at {}",
	            request.getRequestURI(),
	            ex
	    );

	    ErrorResponse response =
	            new ErrorResponse(
	                    false,
	                    "A database error occurred",
	                    "DATABASE_ERROR",
	                    HttpStatus.INTERNAL_SERVER_ERROR.value()
	            );

	    return ResponseEntity
	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(response);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse>
	        handleEntityNotFound(
	                EntityNotFoundException ex,
	                HttpServletRequest request) {

	    ErrorResponse response =
	            new ErrorResponse(
	                    false,
	                    "Requested entity was not found",
	                    "ENTITY_NOT_FOUND",
	                    HttpStatus.NOT_FOUND.value()
	            );

	    return ResponseEntity
	            .status(HttpStatus.NOT_FOUND)
	            .body(response);
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<ErrorResponse>
	        handleEmptyResult(
	                EmptyResultDataAccessException ex,
	                HttpServletRequest request) {

	    ErrorResponse response =
	            new ErrorResponse(
	                    false,
	                    "Requested data was not found",
	                    "DATA_NOT_FOUND",
	                    HttpStatus.NOT_FOUND.value()
	            );

	    return ResponseEntity
	            .status(HttpStatus.NOT_FOUND)
	            .body(response);
	}

	    @ExceptionHandler(ArithmeticException.class)
	    public ResponseEntity<String> handleArithmeticException(
	            ArithmeticException ex) {

	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body("Arithmetic operation is invalid");
	    }
	
}

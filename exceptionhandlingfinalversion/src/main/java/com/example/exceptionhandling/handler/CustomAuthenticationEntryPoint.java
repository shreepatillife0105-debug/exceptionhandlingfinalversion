package com.example.exceptionhandling.handler;
import com.example.exceptionhandling.exception.ErrorCode;
import com.example.exceptionhandling.model.ErrorResponse;
import com.example.exceptionhandling.model.UserIdentity;
import com.example.exceptionhandling.util.RequestContextUtil;

import tools.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Handles authentication failures.
 *
 * Example:
 *
 * Missing JWT
 * Invalid JWT
 * Expired authentication
 * Invalid credentials
 *
 * Result:
 * HTTP 401
 */
@Component
public class CustomAuthenticationEntryPoint  implements AuthenticationEntryPoint{
	
	 private final ObjectMapper objectMapper;

	    public CustomAuthenticationEntryPoint(
	            tools.jackson.databind.ObjectMapper objectMapper
	    ) {
	        this.objectMapper = objectMapper;
	    }

	    @Override
	    public void commence(
	            HttpServletRequest request,
	            HttpServletResponse response,
	            AuthenticationException authException
	    ) throws IOException {

	        String requestId =
	                RequestContextUtil
	                        .getRequestId(request);

	        ErrorResponse errorResponse =
	                new ErrorResponse(
	                        false,
	                        LocalDateTime.now(),
	                        requestId,
	                        ErrorCode.UNAUTHORIZED.name(),
	                        ErrorCode.UNAUTHORIZED
	                                .getDefaultMessage(),
	                        401,
	                        request.getRequestURI(),
	                        request.getMethod(),
	                        new UserIdentity(
	                                "ANONYMOUS",
	                                "ANONYMOUS"
	                        ),
	                        null
	                );

	        response.setStatus(401);

	        response.setContentType(
	                "application/json"
	        );

	        response.setCharacterEncoding("UTF-8");

	        response.setHeader(
	                "X-Request-ID",
	                requestId
	        );

	        objectMapper .writeValue(
	                response.getWriter(),
	                errorResponse
	        );
	    }
	        
}

package com.example.exceptionhandling.handler;

import com.example.exceptionhandling.exception.ErrorCode;
import com.example.exceptionhandling.model.ErrorResponse;
import com.example.exceptionhandling.util.RequestContextUtil;

import tools.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Handles authorization failures.
 *
 * User is authenticated but doesn't have
 * sufficient permission.
 *
 * Result:
 * HTTP 403
 */
@Component
public class CustomAccessDeniedHandler   implements AccessDeniedHandler{

	private final ObjectMapper objectMapper;

    public CustomAccessDeniedHandler(
            ObjectMapper objectMapper
    ) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {

        String requestId =
                RequestContextUtil
                        .getRequestId(request);

        ErrorResponse errorResponse =
                new ErrorResponse(
                        false,
                        LocalDateTime.now(),
                        requestId,
                        ErrorCode.FORBIDDEN.name(),
                        ErrorCode.FORBIDDEN
                                .getDefaultMessage(),
                        403,
                        request.getRequestURI(),
                        request.getMethod(),
                        RequestContextUtil
                                .getCurrentUser(),
                        null
                );

        response.setStatus(403);

        response.setContentType(
                "application/json"
        );

        response.setCharacterEncoding("UTF-8");

        response.setHeader(
                "X-Request-ID",
                requestId
        );

        objectMapper.writeValue(
                response.getWriter(),
                errorResponse
        );
    }
	
}

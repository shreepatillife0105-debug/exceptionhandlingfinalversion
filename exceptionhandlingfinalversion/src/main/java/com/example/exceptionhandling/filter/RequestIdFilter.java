package com.example.exceptionhandling.filter;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Generates a unique ID for every HTTP request.
 *
 * Example:
 *
 * Request ID:
 * 9d5b8d8c-7a6e-4f10-a123-123456789abc
 *
 * WHY?
 *
 * Suppose user reports:
 *
 * "I got an error."
 *
 * They provide requestId.
 *
 * You search the server logs using that requestId
 * and immediately find the exact request.
 */

@Component
public class RequestIdFilter extends OncePerRequestFilter {

	 public static final String REQUEST_ID_HEADER =
	            "X-Request-ID";

	    public static final String REQUEST_ID_ATTRIBUTE =
	            "REQUEST_ID";

	    @Override
	    protected void doFilterInternal(
	            HttpServletRequest request,
	            HttpServletResponse response,
	            FilterChain filterChain
	    ) throws ServletException, IOException {

	        String requestId =
	                request.getHeader(REQUEST_ID_HEADER);

	        // If client didn't provide a request ID,
	        // generate one ourselves.
	        if (requestId == null ||
	                requestId.isBlank()) {

	            requestId =
	                    UUID.randomUUID().toString();
	        }

	        try {

	            request.setAttribute(
	                    REQUEST_ID_ATTRIBUTE,
	                    requestId
	            );

	            // Put requestId into MDC.
	            // Logging frameworks can automatically
	            // include it in log messages.
	            MDC.put(
	                    REQUEST_ID_ATTRIBUTE,
	                    requestId
	            );

	            // Return request ID to client.
	            response.setHeader(
	                    REQUEST_ID_HEADER,
	                    requestId
	            );

	            filterChain.doFilter(
	                    request,
	                    response
	            );

	        } finally {

	            // VERY IMPORTANT:
	            // Remove MDC value after request.
	            // Otherwise values can leak between
	            // reused application threads.
	            MDC.remove(REQUEST_ID_ATTRIBUTE);
	        }
	    }
	
}

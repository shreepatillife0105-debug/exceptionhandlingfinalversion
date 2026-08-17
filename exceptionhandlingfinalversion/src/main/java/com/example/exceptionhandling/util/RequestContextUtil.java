package com.example.exceptionhandling.util;

import com.example.exceptionhandling.filter.RequestIdFilter;
import com.example.exceptionhandling.model.UserIdentity;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * Utility class for request-related information.
 */
public class RequestContextUtil {


    private RequestContextUtil() {
    }

    /**
     * Get request ID.
     */
    public static String getRequestId(
            HttpServletRequest request
    ) {

        Object value =
                request.getAttribute(
                        RequestIdFilter.REQUEST_ID_ATTRIBUTE
                );

        if (value == null) {
            return "UNKNOWN";
        }

        return value.toString();
    }

    /**
     * Identify current authenticated user.
     *
     * IMPORTANT:
     *
     * Authentication#getName() is commonly:
     * - username
     * - email
     * - JWT subject
     *
     * If your application needs the actual database user ID,
     * use a custom principal containing userId.
     */
    public static UserIdentity getCurrentUser() {

    	Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            return new UserIdentity(
                    "ANONYMOUS",
                    "ANONYMOUS"
            );
        }

        String username =
                authentication.getName();

        return new UserIdentity(
                username,
                username
        );
    }
	
}

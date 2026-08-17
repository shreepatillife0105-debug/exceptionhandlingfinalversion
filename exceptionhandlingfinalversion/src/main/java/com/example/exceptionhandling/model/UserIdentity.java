package com.example.exceptionhandling.model;

/**
 * Represents the user associated with the request.
 *
 * For authenticated requests:
 *
 * userId   = actual application user ID
 * username = username/email/etc.
 *
 * For anonymous requests:
 *
 * userId   = ANONYMOUS
 */

public class UserIdentity {

	 private String userId;
	    private String username;

	    public UserIdentity() {
	    }

	    public UserIdentity(
	            String userId,
	            String username
	    ) {
	        this.userId = userId;
	        this.username = username;
	    }

	    public String getUserId() {
	        return userId;
	    }

	    public void setUserId(String userId) {
	        this.userId = userId;
	    }

	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }
	
}

package com.socialmediaapp.exceptions;

import org.springframework.validation.Errors;

public class InvalidRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
    private final String errorCode;
    public InvalidRequestException(String message, String errorCode) {
		super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

}

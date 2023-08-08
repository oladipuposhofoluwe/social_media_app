package com.socialmediaapp.exceptions;


public class DuplicateEntityException extends RuntimeException {

  private final static String ERROR_CODE = "409";
  private static final long serialVersionUID = 1L;

    public DuplicateEntityException(String errorCode, String message) {
        super(errorCode);
    }

    public DuplicateEntityException(String message) {
        super(message);
    }
}

package com.socialmediaapp.exceptions;

public class InvalidOperationException extends RuntimeException {

  private final static String ERROR_CODE = "400";

  private static final long serialVersionUID = 1L;

  public InvalidOperationException() {
    super("Not authorized");
  }

  public InvalidOperationException(String errorCode, String message) {
    super(errorCode);
  }

  public InvalidOperationException(String message) {
    super(message);
  }

  public InvalidOperationException(String message, Throwable e) {
    super(message);
  }
}

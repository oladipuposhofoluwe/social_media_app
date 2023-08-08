package com.socialmediaapp.apiresponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiDataResponse<T> {

  private HttpStatus status;
  private String message;
  private String errorCode;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;
  private T data;
  private ApiDataResponse() {
    timestamp = LocalDateTime.now();
  }

  public ApiDataResponse(HttpStatus status) {
    this();
    this.status = status;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

}
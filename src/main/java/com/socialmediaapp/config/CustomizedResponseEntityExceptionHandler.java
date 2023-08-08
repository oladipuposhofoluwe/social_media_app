package com.socialmediaapp.config;

import com.socialmediaapp.exceptions.*;
import com.socialmediaapp.utils.ApiResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomizedResponseEntityExceptionHandler.class);

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity handleInvalidException(InvalidRequestException e, WebRequest request) {
        return ApiResponseUtils.errorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), e.getLocalizedMessage(), e.getErrorCode());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handleResourceNotFoundException(ResourceNotFoundException e) {
        return ApiResponseUtils.errorResponse(HttpStatus.NOT_FOUND,e.getMessage());
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity handleUnAuthorisedExceptions(UnAuthorizedException e) {
        return ApiResponseUtils.errorResponse(HttpStatus.FORBIDDEN,e.getMessage());
    }
}

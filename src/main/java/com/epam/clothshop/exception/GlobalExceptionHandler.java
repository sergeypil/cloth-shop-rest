package com.epam.clothshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler()
    public ResponseEntity handleResourceNotFound(ResourceNotFoundException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler()
    public ResponseEntity handleResourceNotFound(Exception e) {
        return new ResponseEntity("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

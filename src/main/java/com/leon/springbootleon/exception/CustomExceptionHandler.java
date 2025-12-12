package com.leon.springbootleon.exception;

import com.leon.springbootleon.model.dto.response.CustomApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler{

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomApiResponse> handleException(Exception e){
        var status =  HttpStatus.INTERNAL_SERVER_ERROR;
        var errorMsg = e.getMessage();
        var displayMsg = "Request Error";
        log.error("-->> Request Error: {}", errorMsg);
        return new ResponseEntity<>(new CustomApiResponse(status, displayMsg), status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomApiResponse> handleConstraintViolationException(ConstraintViolationException e){
        var status =  HttpStatus.BAD_REQUEST;
        var errorMsg = e.getMessage();
        return new ResponseEntity<>(new CustomApiResponse(status, errorMsg), status);
    }
}

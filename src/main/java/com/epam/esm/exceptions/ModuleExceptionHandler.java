package com.epam.esm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class ModuleExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<ModuleErrorResponse> handleException(MethodArgumentNotValidException exception){
        return new ResponseEntity<>(new ModuleErrorResponse(
                Objects.requireNonNull(exception.getFieldError()).getDefaultMessage(),
                "40001"),
                exception.getStatusCode());
    }

    @ExceptionHandler
    private ResponseEntity<ModuleErrorResponse> handleException(HttpMessageNotReadableException exception){
        return new ResponseEntity<>(new ModuleErrorResponse(
                exception.getLocalizedMessage(),
                "40002"),
                HttpStatus.BAD_REQUEST);
    }

}

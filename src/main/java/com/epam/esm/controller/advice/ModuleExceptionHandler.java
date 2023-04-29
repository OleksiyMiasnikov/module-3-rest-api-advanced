package com.epam.esm.controller.advice;

import com.epam.esm.exception.ModuleErrorResponse;
import com.epam.esm.exception.ModuleException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ModuleExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<ModuleErrorResponse> handleException(ModuleException exception){
        return new ResponseEntity<>(new ModuleErrorResponse(
                exception.getMessage(),
                exception.getErrorCode()),
                exception.getHttpStatusCode());
    }

    @ExceptionHandler
    private ResponseEntity<ModuleErrorResponse> handleException(BindException exception){
        return new ResponseEntity<>(new ModuleErrorResponse(
                exception.getMessage(),
                "40001"),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    private ResponseEntity<ModuleErrorResponse> handleException(TypeMismatchException exception){
        return new ResponseEntity<>(new ModuleErrorResponse(
                exception.getMessage(),
                "40002"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ModuleErrorResponse> handleException(HttpMessageConversionException exception){
        return new ResponseEntity<>(new ModuleErrorResponse(
                exception.getMessage(),
                "40003"),
                HttpStatus.BAD_REQUEST);
    }
}

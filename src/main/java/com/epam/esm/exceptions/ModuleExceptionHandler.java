package com.epam.esm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class ModuleExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<ModuleErrorResponse> handleException(ModuleException exception){
        return new ResponseEntity<>(new ModuleErrorResponse(exception),
                HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    private ResponseEntity<ModuleErrorResponse> handleException(NumberFormatException exception){
        return new ResponseEntity<>(new ModuleErrorResponse(
                "Incorrect value",
                "40002"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ModuleErrorResponse> handleException(SQLException exception){
        return new ResponseEntity<>(new ModuleErrorResponse(
                "Database error!",
                "50001"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

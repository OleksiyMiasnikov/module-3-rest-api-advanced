package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class ModuleException extends RuntimeException{
    private String message;
    private String errorCode;
    private HttpStatus htmlStatus;
}


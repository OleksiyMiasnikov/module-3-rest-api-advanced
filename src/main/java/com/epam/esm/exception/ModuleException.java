package com.epam.esm.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuleException extends RuntimeException{
    private String message;
    private String errorCode;
    public ModuleException(String message) {
        this.message = message;
    }
}

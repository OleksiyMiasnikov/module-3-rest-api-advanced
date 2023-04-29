package com.epam.esm.exception;

public class UserNotFoundException extends ModuleException{

    public UserNotFoundException(String message) {
        super(message);
    }
}

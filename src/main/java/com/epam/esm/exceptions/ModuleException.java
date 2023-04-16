package com.epam.esm.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.validation.BindingResult;

import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class ModuleException extends RuntimeException{
    private String message;
    private String errorCode;

    public ModuleException(BindingResult bindingResult) {
        this.errorCode = Objects.requireNonNull(bindingResult.getFieldError()).getCode();
        this.message = bindingResult.getFieldError().getDefaultMessage();
    }
}


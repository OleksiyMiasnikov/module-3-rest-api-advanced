package com.epam.esm.validator;

import com.epam.esm.exception.ModuleException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class SortingValidator  {
    public void validate(String sort){
        if (sort == null || sort.isEmpty()) return;
        if (!sort.equals("ASC") && !sort.equals("DESC")) {
            throw new ModuleException("Field 'sort_by' must be ASC or DESC", "40431", HttpStatus.BAD_REQUEST);
        }
    }
}

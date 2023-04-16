package com.epam.esm.validators;

import com.epam.esm.models.Certificate;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CertificateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Certificate.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors,
                "name",
                "40411",
                "Field 'name' can not be empty!");
        ValidationUtils.rejectIfEmpty(errors,
                "description",
                "40412",
                "Field 'description' can not be empty!");

        Certificate certificate = (Certificate) target;

        if (certificate.getPrice() == null || certificate.getPrice() <= 0) {
            errors.rejectValue("price",
                    "40413",
                    "Field 'price' should be more then 0!");
        }
        if (certificate.getDuration() == null || certificate.getDuration() <= 0) {
            errors.rejectValue("duration",
                    "40414",
                    "Field 'duration' should be more then 0!");
        }
    }
}

package com.epam.esm.validators;

import com.epam.esm.models.Certificate;
import com.epam.esm.models.CertificateWithTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class CertificateWithTagValidator implements Validator {

    private final CertificateValidator certificateValidator;

    @Override
    public boolean supports(Class<?> clazz) {
        return CertificateWithTag.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors,
                "tag",
                "40421",
                "Field 'tag' can not be empty!");
        CertificateWithTag certificateWithTag = (CertificateWithTag) target;
        ValidationUtils.invokeValidator(this.certificateValidator, Certificate.mapper(certificateWithTag), errors);
    }
}

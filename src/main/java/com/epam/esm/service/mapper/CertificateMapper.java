package com.epam.esm.service.mapper;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.CertificateWithTag;
import org.springframework.stereotype.Component;

@Component
public class CertificateMapper {
    public Certificate toCertificate(CertificateWithTag certificateWithTag) {
        return Certificate.builder()
                .name(certificateWithTag.getName())
                .description(certificateWithTag.getDescription())
                .price(certificateWithTag.getPrice())
                .duration(certificateWithTag.getDuration())
                .build();
    }
}

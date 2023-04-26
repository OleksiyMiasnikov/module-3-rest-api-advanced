package com.epam.esm.service.mapper;

import com.epam.esm.model.DTO.certificate.CertificateDTO;
import com.epam.esm.model.DTO.certificate.CreateCertificateRequest;
import com.epam.esm.model.DTO.certificate_with_tag.CertificateWithTagRequest;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.util.DateUtil;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


@Component
public class CertificateMapper {
    private static final String PATTERN_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public Certificate toCertificate(CertificateWithTagRequest request) {
        return Certificate.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .duration(request.getDuration())
                .build();
    }

    public Certificate toCertificate(CreateCertificateRequest request) {
        return Certificate.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .duration(request.getDuration())
                .createDate(DateUtil.getDate())
                .lastUpdateDate(DateUtil.getDate())
                .build();
    }

    public CertificateDTO toDTO(Certificate certificate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
                .withZone(ZoneId.systemDefault());
        if (certificate.getCreateDate() == null) {
            certificate.setCreateDate(Instant.EPOCH);
        }
        if (certificate.getLastUpdateDate() == null) {
            certificate.setLastUpdateDate(Instant.EPOCH);
        }
        return CertificateDTO.builder()
                .id(certificate.getId())
                .name(certificate.getName())
                .description(certificate.getDescription())
                .price(certificate.getPrice())
                .duration(certificate.getDuration())
                .createDate(formatter.format(certificate.getCreateDate()))
                .lastUpdateDate(formatter.format(certificate.getLastUpdateDate()))
                .build();
    }
}

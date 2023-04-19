package com.epam.esm.service.mapper;

import com.epam.esm.model.DTO.certificate_with_tag.CertificateWithTagDTO;
import com.epam.esm.model.DTO.certificate_with_tag.CertificateWithTagRequest;
import com.epam.esm.model.entity.CertificateWithTag;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class CertificateWithTagMapper {
    private static final String PATTERN_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public CertificateWithTag toCertificateWithTag(CertificateWithTagRequest request) {
        return CertificateWithTag.builder()
                .tag(request.getTag())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .duration(request.getDuration())
                .build();
    }

    public CertificateWithTagDTO toDTO(CertificateWithTag certificateWithTag) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
                .withZone(ZoneId.systemDefault());
        return CertificateWithTagDTO.builder()
                .id(certificateWithTag.getId())
                .tag(certificateWithTag.getTag())
                .name(certificateWithTag.getName())
                .description(certificateWithTag.getDescription())
                .price(certificateWithTag.getPrice())
                .duration(certificateWithTag.getDuration())
                .createDate(formatter.format(certificateWithTag.getCreateDate()))
                .lastUpdateDate(formatter.format(certificateWithTag.getLastUpdateDate()))
                .build();
    }
}

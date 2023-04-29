package com.epam.esm.service.mapper;

import com.epam.esm.model.DTO.certificate.CertificateDTO;
import com.epam.esm.model.DTO.certificate.CreateCertificateRequest;
import com.epam.esm.model.DTO.certificate_with_tag.CertificateWithTagRequest;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


@Component
public class CertificateMapper {
    private static final String PATTERN_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private final ModelMapper mapper = new ModelMapper();

    public Certificate toCertificate(CertificateWithTagRequest request) {
        return mapper.map(request, Certificate.class);
    }

    public Certificate toCertificate(CreateCertificateRequest request) {
        TypeMap<CreateCertificateRequest, Certificate> propertyMapper =
                mapper.createTypeMap(CreateCertificateRequest.class, Certificate.class);
        propertyMapper.addMappings(
                mapper -> mapper.map((certificateRequest) -> DateUtil.getDate(),
                        Certificate::setCreateDate));
        propertyMapper.addMappings(
                mapper -> mapper.map((certificateRequest) -> DateUtil.getDate(),
                        Certificate::setLastUpdateDate));
        return mapper.map(request,Certificate.class);
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
//        TypeMap<Certificate, CertificateDTO> propertyMapper =
//                mapper.createTypeMap(Certificate.class, CertificateDTO.class);
//        propertyMapper.addMappings(
//                mapper -> mapper.map((cert) -> formatter.format(cert.getCreateDate()),
//                CertificateDTO::setCreateDate));
//        propertyMapper.addMappings(
//                mapper -> mapper.map((cert) -> formatter.format(cert.getLastUpdateDate()),
//                        CertificateDTO::setLastUpdateDate));
//        return mapper.map(certificate, CertificateDTO.class);

    }
}

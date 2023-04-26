package com.epam.esm.service.mapper;

import com.epam.esm.controller.CertificateWithTagController;
import com.epam.esm.exception.ModuleException;
import com.epam.esm.model.DTO.certificate_with_tag.CertificateWithTagDTO;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class CertificateWithTagMapper {

    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;

    private static final String PATTERN_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public CertificateWithTagDTO toDTO(CertificateWithTag certificateWithTag) {

        Optional<Certificate> certificateOptional = certificateRepository.findById(certificateWithTag.getCertificateId());
        Optional<Tag> tagOptional = tagRepository.findById(certificateWithTag.getTagId());

        if (certificateOptional.isEmpty() || tagOptional.isEmpty()) {
            throw new ModuleException("Error","50001", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Certificate certificate = certificateOptional.get();
        Tag tag = tagOptional.get();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
                .withZone(ZoneId.systemDefault());

        CertificateWithTagDTO certificateWithTagDTO = CertificateWithTagDTO.builder()
                .id(certificateWithTag.getId())
                .tag(tag.getName())
                .name(certificate.getName())
                .description(certificate.getDescription())
                .price(certificate.getPrice())
                .duration(certificate.getDuration())
                .createDate(formatter.format(certificate.getCreateDate()))
                .lastUpdateDate(formatter.format(certificate.getLastUpdateDate()))
                .build();
        certificateWithTagDTO.add(linkTo(methodOn(CertificateWithTagController.class)
                .findById(certificateWithTag.getId()))
                .withSelfRel());
        return certificateWithTagDTO;
    }
}

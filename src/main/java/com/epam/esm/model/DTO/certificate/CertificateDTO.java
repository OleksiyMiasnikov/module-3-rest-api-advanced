package com.epam.esm.model.DTO.certificate;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDTO extends RepresentationModel<CertificateDTO> {
    private static final String PATTERN_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
            .withZone(ZoneId.systemDefault());
    private int id;
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private String createDate;
    private String lastUpdateDate;

    public void setCreateDate(Instant date){
        this.createDate = formatter.format(Objects.requireNonNullElse(date, Instant.EPOCH));
    }

    public void setLastUpdateDate(Instant date){
        this.lastUpdateDate = formatter.format(Objects.requireNonNullElse(date, Instant.EPOCH));
    }
}

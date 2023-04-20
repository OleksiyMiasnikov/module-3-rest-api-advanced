package com.epam.esm.model.DTO.certificate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CertificateDTO {
    private int id;
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private String createDate;
    private String lastUpdateDate;

}

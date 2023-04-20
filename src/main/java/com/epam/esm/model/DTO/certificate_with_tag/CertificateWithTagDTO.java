package com.epam.esm.model.DTO.certificate_with_tag;

import lombok.*;

@Getter
@Builder
public class CertificateWithTagDTO {
    private int id;
    private String tag;
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private String createDate;
    private String lastUpdateDate;
}

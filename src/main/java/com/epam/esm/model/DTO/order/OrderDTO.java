package com.epam.esm.model.DTO.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private int id;
    private String userName;
    private int CertificateWithTagId;
    private String tagName;
    private String certificateName;
    private String description;
    private Integer duration;
    private Double cost;
    private String createDate;
}

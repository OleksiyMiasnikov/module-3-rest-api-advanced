package com.epam.esm.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private int id;
    private int userId;
    private String userName;
    private int CertificateWithTagId;
    private String tagName;
    private String certificateName;
    private String description;
    private Integer duration;
    private Double cost;
    private Instant createDate;
}

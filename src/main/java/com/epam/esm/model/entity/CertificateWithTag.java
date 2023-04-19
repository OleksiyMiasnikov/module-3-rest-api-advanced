package com.epam.esm.model.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateWithTag {
    private int id;
    private String tag;
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private Instant createDate;
    private Instant lastUpdateDate;
}

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
    private int userId;
    private int CertificateWithTagId;
    private Double cost;
    private String createDate;
}

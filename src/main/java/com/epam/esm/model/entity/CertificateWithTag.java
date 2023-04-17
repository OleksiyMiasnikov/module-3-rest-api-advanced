package com.epam.esm.model.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateWithTag {
    @NotEmpty(message = "Field 'tag' can not be empty!")
    private String tag;
    @NotEmpty(message = "Field 'name' can not be empty!")
    private String name;
    @NotEmpty(message = "Field 'description' can not be empty!")
    private String description;
    @NotNull(message = "Field 'price' can not be empty!")
    @DecimalMin(value = "0.01", message = "Field 'duration' should be more then 0!")
    private Double price;
    @NotNull(message = "Field 'duration' can not be empty!")
    @Min(value = 1, message = "Field 'duration' should be more then 0!")
    private Integer duration;
    private String createDate;
    private String lastUpdateDate;
}

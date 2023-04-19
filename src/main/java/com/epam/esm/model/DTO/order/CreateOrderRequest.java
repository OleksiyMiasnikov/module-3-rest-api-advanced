package com.epam.esm.model.DTO.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class CreateOrderRequest {
    @NotNull(message = "Field 'userId' can not be empty!")
    @Min(value = 1, message = "Field 'userId' should be more then 0!")
    private int userId;
    @NotNull(message = "Field 'certificateWithTagId' can not be empty!")
    @Min(value = 1, message = "Field 'certificateWithTagId' should be more then 0!")
    private int certificateWithTagId;
}
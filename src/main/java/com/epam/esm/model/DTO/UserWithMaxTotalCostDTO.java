package com.epam.esm.model.DTO;

import lombok.*;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class UserWithMaxTotalCostDTO {
    private String user;
    private String tag;
    private Double totalCost;
}

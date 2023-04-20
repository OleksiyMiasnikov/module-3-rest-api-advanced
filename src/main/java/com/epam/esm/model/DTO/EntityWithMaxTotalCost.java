package com.epam.esm.model.DTO;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class EntityWithMaxTotalCost {
    private String user;
    private String tag;
    private Double totalCost;


}

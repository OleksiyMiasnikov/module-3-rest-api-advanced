package com.epam.esm.model.DTO;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@Builder
@Component
public class SortingEntity {
    private String field;
    @Pattern(regexp = "ASC|DESC", message = "parameter 'direction' should be 'ASC' or 'DESC'")
    String direction;
}

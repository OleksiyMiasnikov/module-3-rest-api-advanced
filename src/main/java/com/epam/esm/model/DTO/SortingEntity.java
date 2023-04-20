package com.epam.esm.model.DTO;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SortingEntity {
    private String sort_by;
    @Pattern(regexp = "ASC|DESC", message = "parameter 'direction' should be 'ASC' or 'DESC'")
    private String direction;
}

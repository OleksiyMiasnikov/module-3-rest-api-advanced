package com.epam.esm.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SortingEntity {

    @JsonProperty("sort_by")
    private String sortBy;
    @Pattern(regexp = "ASC|DESC", message = "parameter 'direction' should be 'ASC' or 'DESC'")
    private String direction;

}

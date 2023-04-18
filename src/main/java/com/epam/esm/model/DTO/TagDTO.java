package com.epam.esm.model.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Builder
public class TagDTO {
    private int id;
    @NotEmpty(message = "Field 'name' can not be empty!")
    private String name;
}

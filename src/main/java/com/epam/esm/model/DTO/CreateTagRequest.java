package com.epam.esm.model.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTagRequest {
    @NotEmpty(message = "Field 'name' can not be empty!")
    private String name;
}

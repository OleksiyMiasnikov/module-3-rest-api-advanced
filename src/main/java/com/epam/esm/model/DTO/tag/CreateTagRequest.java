package com.epam.esm.model.DTO.tag;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTagRequest {
    @NotEmpty(message = "Field 'name' can not be empty!")
    private String name;
}

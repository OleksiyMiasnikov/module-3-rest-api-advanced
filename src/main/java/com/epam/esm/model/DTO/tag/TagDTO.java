package com.epam.esm.model.DTO.tag;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TagDTO {
    private int id;
    private String name;
}

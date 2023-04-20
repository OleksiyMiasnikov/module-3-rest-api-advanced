package com.epam.esm.model.DTO.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {
    private int id;
    private String name;
}

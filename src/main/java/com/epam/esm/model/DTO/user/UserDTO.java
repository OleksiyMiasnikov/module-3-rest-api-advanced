package com.epam.esm.model.DTO.user;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class UserDTO {
    private int id;
    private String name;
}

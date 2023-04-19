package com.epam.esm.service.mapper;

import com.epam.esm.model.DTO.tag.CreateTagRequest;
import com.epam.esm.model.DTO.tag.TagDTO;
import com.epam.esm.model.DTO.user.UserDTO;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDTO (User user){
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}

package com.epam.esm.service.mapper;

import com.epam.esm.model.DTO.tag.CreateTagRequest;
import com.epam.esm.model.DTO.tag.TagDTO;
import com.epam.esm.model.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    public TagDTO toDTO (Tag tag){
        return TagDTO.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }

    public Tag toTag (CreateTagRequest createTagRequest){
        return Tag.builder()
                .name(createTagRequest.getName())
                .build();
    }
}

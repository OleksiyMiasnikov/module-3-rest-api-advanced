package com.epam.esm.service.mapper;

import com.epam.esm.model.DTO.UserWithMaxTotalCostDTO;
import com.epam.esm.model.entity.UserWithMaxTotalCost;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserWithMaxTotalCostMapper {

    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    public UserWithMaxTotalCostDTO toDTO(UserWithMaxTotalCost entity) {
        return UserWithMaxTotalCostDTO.builder()
                .user(userRepository.findById(entity.getUser_id()).get().getName())
                .tag(tagRepository.findById(entity.getTag_id()).get().getName())
                .totalCost(entity.getTotalCost())
                .build();
    }
}

package com.epam.esm.service.mapper;

import com.epam.esm.model.DTO.SortingEntity;
import org.springframework.stereotype.Component;

@Component
public class SortingEntityMapper {
    public SortingEntity toSortBy(SortingEntity sortingEntity) {
        String field;
        String direction;

        if (sortingEntity.getField() == null) {
            field = "name";
        } else {
            field = switch (sortingEntity.getField()) {
                case "createDate" -> "create_date";
                case "lastUpdateDate" -> "last_update_date";
                default -> sortingEntity.getField();
            };
        }

        if (sortingEntity.getDirection() == null) {
            direction = "ASC";
        } else {
            direction = sortingEntity.getDirection();
        }

        return SortingEntity.builder()
                .field(field)
                .direction(direction)
                .build();
    }
}

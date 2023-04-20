package com.epam.esm.service.mapper;

import com.epam.esm.model.DTO.SortingEntity;
import org.springframework.stereotype.Component;

@Component
public class SortingEntityMapper {
    public SortingEntity toSortBy(SortingEntity sortingEntity) {
        String sortBy;
        String direction;

        if (sortingEntity.getSort_by() == null) {
            sortBy = "name";
        } else {
            sortBy = switch (sortingEntity.getSort_by()) {
                case "id" -> "tag_tb.id";
                case "tag" -> "tag_name";
                case "createDate" -> "create_date";
                case "lastUpdateDate" -> "last_update_date";
                default -> sortingEntity.getSort_by();
            };
        }

        if (sortingEntity.getDirection() == null) {
            direction = "ASC";
        } else {
            direction = sortingEntity.getDirection();
        }

        return SortingEntity.builder()
                .sort_by(sortBy)
                .direction(direction)
                .build();
    }
}

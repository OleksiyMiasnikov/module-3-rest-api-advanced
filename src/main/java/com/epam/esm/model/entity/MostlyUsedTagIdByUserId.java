package com.epam.esm.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "mostly_used_tag_id_by_user_id")
public class MostlyUsedTagIdByUserId {
    @Id
    private Integer tagId;
    private Long countTag;
}

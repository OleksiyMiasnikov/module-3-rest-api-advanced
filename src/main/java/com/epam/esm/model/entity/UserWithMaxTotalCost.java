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
@Table(name = "user_with_max_total_cost")
public class UserWithMaxTotalCost {
    @Id
    private Integer userId;
    private Integer tagId;
    private Double totalCost;
}

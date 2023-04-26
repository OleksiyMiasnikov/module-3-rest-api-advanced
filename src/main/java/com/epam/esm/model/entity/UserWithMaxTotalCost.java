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
    private Double totalCost;
}
/*
@Projection(name = "deadline", types = { ABDeadlineType.class })
public interface DeadlineType {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.code}")
    String getText();

}
 */
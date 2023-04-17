package com.epam.esm.model.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Certificate {
    private int id;
    @NotEmpty(message = "Field 'name' can not be empty!")
    private String name;
    @NotEmpty(message = "Field 'description' can not be empty!")
    private String description;
    @NotNull(message = "Field 'price' can not be empty!")
    @Min(value = 1, message = "Field 'price' should be more then 0!")
    private Double price;
    @NotNull(message = "Field 'duration' can not be empty!")
    @Min(value = 1, message = "Field 'duration' should be more then 0!")
    private Integer duration;
    private String createDate;
    private String lastUpdateDate;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Certificate that = (Certificate) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, duration);
    }
}

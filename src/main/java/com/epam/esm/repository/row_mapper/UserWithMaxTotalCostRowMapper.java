package com.epam.esm.repository.row_mapper;

import com.epam.esm.model.entity.UserWithMaxTotalCost;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserWithMaxTotalCostRowMapper implements RowMapper<UserWithMaxTotalCost> {

    @Override
    public UserWithMaxTotalCost mapRow(ResultSet rs, int rowNum) throws SQLException {
        return UserWithMaxTotalCost.builder()
                .totalCost(rs.getDouble("sum_of_costs"))
                .user_id(rs.getInt("user_id"))
                .build();
    }
}

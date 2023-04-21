package com.epam.esm.repository.row_mapper;

import com.epam.esm.model.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class OrderRowMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Order.builder()
                .id(rs.getInt("id"))
                .userId(rs.getInt("user_id"))
                .userName(rs.getString("user_name"))
                .CertificateWithTagId(rs.getInt("certificate_with_tag_id"))
                .tagName(rs.getString("tag_name"))
                .certificateName(rs.getString("certificate_name"))
                .description(rs.getString("description"))
                .duration(rs.getInt("duration"))
                .cost(rs.getDouble("cost"))
                .createDate(rs.getTimestamp("create_date").toInstant())
                .build();
    }
}

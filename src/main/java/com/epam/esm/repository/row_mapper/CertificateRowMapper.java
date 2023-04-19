package com.epam.esm.repository.row_mapper;

import com.epam.esm.model.entity.Certificate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class CertificateRowMapper implements RowMapper<Certificate> {
    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Certificate.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .price(rs.getDouble("price"))
                .duration(rs.getInt("duration"))
                .createDate(rs.getTimestamp("create_date").toInstant())
                .lastUpdateDate(rs.getTimestamp("last_update_date").toInstant())
                .build();
    }
}

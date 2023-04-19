package com.epam.esm.repository.row_mapper;

import com.epam.esm.model.entity.CertificateWithTag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class CertificateWithTagRowMapper implements RowMapper<CertificateWithTag> {
    @Override
    public CertificateWithTag mapRow(ResultSet rs, int rowNum) throws SQLException {
        return CertificateWithTag.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("certificate_name"))
                .description(rs.getString("description"))
                .price(rs.getDouble("price"))
                .duration(rs.getInt("duration"))
                .createDate(rs.getTimestamp("create_date").toInstant())
                .lastUpdateDate(rs.getTimestamp("last_update_date").toInstant())
                .tag(rs.getString("tag_name"))
                .build();
    }
}

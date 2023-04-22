package com.epam.esm.repository;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.repository.row_mapper.CertificateRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CertificateRepository {

    private final JdbcTemplate jdbcTemplate;

    public int create(Certificate certificate) {
        log.info("Repository. Create certificate with name: " + certificate.getName());
        final String SQL = "INSERT INTO certificate VALUES (default, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, certificate.getName());
            ps.setString(2, certificate.getDescription());
            ps.setDouble(3, certificate.getPrice());
            ps.setInt(4, certificate.getDuration());
            ps.setTimestamp(5, Timestamp.from(certificate.getCreateDate()));
            ps.setTimestamp(6, Timestamp.from(certificate.getLastUpdateDate()));
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public List<Certificate> findAll(int page, int size) {
        log.info("Repository. Find all certificates");
        return jdbcTemplate.query("SELECT * FROM certificate LIMIT ? OFFSET ?",
                new CertificateRowMapper(),
                size,
                (page - 1) * size);
    }

    public Optional<Certificate> findById(int id){
        log.info("Repository. Find certificate by id: " + id);
        return jdbcTemplate.query("SELECT * FROM certificate WHERE id=?",
                        new CertificateRowMapper(),
                        id)
                .stream()
                .findAny();
    }

    public void update(int id, Certificate certificate) {
        log.info("Repository. Update certificate by id: " + id);
        jdbcTemplate.update("UPDATE certificate " +
                        "SET name=?, " +
                            "description=?, " +
                            "price=?, " +
                            "duration=?, " +
                            "last_update_date=? " +
                        "WHERE id=?",
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                certificate.getLastUpdateDate(),
                id);
    }

    public boolean delete(int id) {
        log.info("Repository. Delete certificate by id: " + id);
        int result = jdbcTemplate.update("DELETE FROM certificate WHERE id=?", id);
        log.info("result of deleting " + result);
        return result == 1;
    }

    public Integer sizeOfCertificate() {
        log.info("Repository. Determine size of Certificate");
        String sql = "SELECT count(*) FROM certificate";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}

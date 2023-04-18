package com.epam.esm.repository;

import com.epam.esm.model.DTO.SortingEntity;
import com.epam.esm.model.entity.CertificateWithTag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CertificateWithTagRepository {

    private final JdbcTemplate jdbcTemplate;
    private final String JOIN_SQL =
            "SELECT " +
                "name as certificate_name, " +
                "description, " +
                "price, " +
                "duration, " +
                "create_date, " +
                "last_update_date, " +
                "certificate_id, " +
                "tag_id, " +
                "tag_name " +
            "FROM  certificate " +
            "JOIN (  " +
                    "SELECT certificate_id, " +
                            "name as tag_name, " +
                            "tag_id " +
                    "FROM certificate_with_tag " +
                    "JOIN tag " +
                    "WHERE certificate_with_tag.tag_id = tag.id" +
                 ") tag_tb " +
            "WHERE tag_tb.certificate_id = certificate.id %s";


    public void create(int tagId, int certificateId){
        log.info("Repository. Create certificate with tag");
        jdbcTemplate.update("INSERT INTO certificate_with_tag (tag_id, certificate_id) VALUES (?, ?)",
                tagId, certificateId);
    }

    public List<CertificateWithTag> findAll(SortingEntity sortingEntity) {
        log.info("Repository. Find all certificates with tags");
        String sql = String.format(JOIN_SQL, "ORDER by " +
                sortingEntity.getField() +
                " " +
                sortingEntity.getDirection());
        return jdbcTemplate.query(sql,
                new CertificateWithTagMapper());
    }

    public List<CertificateWithTag> findByTagName(String name, SortingEntity sortingEntity) {
        log.info("Repository. Find all certificates with tag: " + name);
        String sql = String.format(JOIN_SQL, "ORDER by " +
                sortingEntity.getField() +
                " " +
                sortingEntity.getDirection());
        return jdbcTemplate.query("SELECT * FROM (" + sql + ") all_tb WHERE all_tb.tag_name=?",
                        new Object[]{name},
                        new CertificateWithTagMapper());
    }

    public Optional<CertificateWithTag> findByTagIdAndCertificateId(Integer tagId, Integer certificateId) {
        log.info("Repository. Find certificate by tagId and certificateId");
        String sql = String.format(JOIN_SQL, "");
        return jdbcTemplate.query("SELECT * FROM (" +
                        sql +
                        ") all_tb WHERE all_tb.tag_id=? AND all_tb.certificate_id=?",
                new Object[]{tagId, certificateId},
                new CertificateWithTagMapper())
                .stream()
                .findAny();
    }

    public List<CertificateWithTag> findByPartOfNameOrDescription(String pattern) {
        log.info("Repository. Find certificate by part of name or description");
        return jdbcTemplate.query("call find_by_part(?)",
                new Object[]{pattern},
                new CertificateWithTagMapper());
    }
}

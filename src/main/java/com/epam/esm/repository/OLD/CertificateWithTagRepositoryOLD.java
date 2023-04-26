package com.epam.esm.repository.OLD;

import com.epam.esm.model.DTO.SortingEntity;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CertificateWithTagRepositoryOLD {

    private final JdbcTemplate jdbcTemplate;
    private final String JOIN_SQL =
            "SELECT " +
                "tag_tb.id as id, name as certificate_name, description, price, duration, " +
                "create_date, last_update_date, certificate_id, tag_id, tag_name " +
            "FROM  certificate " +
            "JOIN (  " +
                    "SELECT certificate_with_tag.id, certificate_id, name as tag_name, tag_id " +
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
                sortingEntity.getSortBy() +
                " " +
                sortingEntity.getDirection());
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(CertificateWithTag.class));
    }

    public List<CertificateWithTag> findByTagName(String name, SortingEntity sortingEntity) {
        log.info("Repository. Find all certificates with tag: " + name);
        String sql = String.format(JOIN_SQL, "ORDER by " +
                sortingEntity.getSortBy() +
                " " +
                sortingEntity.getDirection());
        return jdbcTemplate.query("SELECT * FROM (" + sql + ") all_tb WHERE all_tb.tag_name=?",
                new BeanPropertyRowMapper<>(CertificateWithTag.class),
                name);
    }

    public List<CertificateWithTag> findByTagName(List<String> list, SortingEntity sortingEntity) {
        log.info("Repository. Find all certificates with tag");
        String inSql = String.join(",", Collections.nCopies(list.size(), "?"));
        String sql = String.format(JOIN_SQL, "ORDER by " +
                sortingEntity.getSortBy() +
                " " +
                sortingEntity.getDirection());
        return jdbcTemplate.query("SELECT * FROM (" +
                        sql +
                        ") all_tb WHERE all_tb.tag_name IN (" +
                        inSql +
                        ")",
                new BeanPropertyRowMapper<>(CertificateWithTag.class),
                list.toArray());
    }
    public List<CertificateWithTag> findAllWithPage(int page, int size) {
        log.info("Repository. Find all certificates with tags");
        String sql = String.format(JOIN_SQL, "LIMIT ? OFFSET ?");
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(CertificateWithTag.class),
                size,
                (page -1) * size);
    }

    public List<CertificateWithTag> findByTagNameWithPage(String name, int page, int size) {
        log.info("Repository. Find all certificates with tag: " + name);
        String sql = String.format(JOIN_SQL, "LIMIT " +
                size +
                " OFFSET " +
                (page -1) * size);
        return jdbcTemplate.query("SELECT * FROM (" + sql + ") all_tb WHERE all_tb.tag_name=?",
                new BeanPropertyRowMapper<>(CertificateWithTag.class),
                name);
    }

    public Optional<CertificateWithTag> findByTagIdAndCertificateId(Integer tagId, Integer certificateId) {
        log.info("Repository. Find certificate by tagId and certificateId");
        String sql = String.format(JOIN_SQL, "");
        return jdbcTemplate.query("SELECT * FROM (" +
                        sql +
                        ") all_tb WHERE all_tb.tag_id=? AND all_tb.certificate_id=?",
                        new BeanPropertyRowMapper<>(CertificateWithTag.class),
                        new Object[]{tagId, certificateId})
                .stream()
                .findAny();
    }

    public List<CertificateWithTag> findByPartOfNameOrDescription(String pattern) {
        log.info("Repository. Find certificate by part of name or description");
        return jdbcTemplate.query("call find_by_part(?)",
                new BeanPropertyRowMapper<>(CertificateWithTag.class),
                pattern);
    }

    public Optional<CertificateWithTag> findById(int id){
        log.info("Repository. Find certificate by id: " + id);
        String sql = String.format(JOIN_SQL, "");
        return jdbcTemplate.query("SELECT * FROM (" + sql + ") all_tb WHERE all_tb.id=?",
                        new BeanPropertyRowMapper<>(CertificateWithTag.class),
                        id)
                .stream()
                .findAny();
    }

    public Integer sizeOfCertificateWithTag() {
        log.info("Repository. Determine size of CertificateWithTag");
        String sql = "SELECT count(*) FROM (" + String.format(JOIN_SQL, ") selected_tab");
        return jdbcTemplate.queryForObject(sql,  Integer.class);
    }
}
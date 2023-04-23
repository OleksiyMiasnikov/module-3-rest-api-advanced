package com.epam.esm.repository;

import com.epam.esm.model.entity.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TagRepository1 {

    private final JdbcTemplate jdbcTemplate;

    public int create(Tag tag) {
        log.info("Repository. Create a new tag");
        final String SQL = "INSERT INTO tag VALUES (default, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public List<Tag> findAll() {
        log.info("Repository. Find all tags");
        return jdbcTemplate.query("SELECT * FROM tag", new BeanPropertyRowMapper<>(Tag.class));
    }

    public Optional<Tag> findById(int id){
        log.info("Repository. Find tag by id: " + id);
        return jdbcTemplate.query("SELECT * FROM tag WHERE id=?",
                new BeanPropertyRowMapper<>(Tag.class),
                id)
                .stream()
                .findAny();
    }

    public List<Tag> findByName(String name){
        log.info("Repository. Find tag by name: " + name);
        return jdbcTemplate.query("SELECT * FROM tag WHERE name=?",
                new BeanPropertyRowMapper<>(Tag.class),
                name);
    }

    public boolean delete(int id) {
        log.info("Repository. Delete tag by id: " + id);
        int result = jdbcTemplate.update("DELETE FROM tag WHERE id=?", id);
        log.info("result of deleting " + result);
        return result == 1;
    }
}

package com.epam.esm.repository;

import com.epam.esm.model.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<User> findAll() {
        log.info("Repository. Find all users");
        return jdbcTemplate.query("SELECT * FROM user",
                new BeanPropertyRowMapper<>(User.class));
    }

    public Optional<User> findById(int id){
        log.info("Repository. Find user by id: " + id);
        return jdbcTemplate.query("SELECT * FROM user WHERE id=?",
                        new BeanPropertyRowMapper<>(User.class),
                        id)
                .stream()
                .findAny();
    }

}

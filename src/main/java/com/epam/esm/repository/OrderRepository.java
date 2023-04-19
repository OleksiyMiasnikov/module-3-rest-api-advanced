package com.epam.esm.repository;

import com.epam.esm.model.entity.Order;
import com.epam.esm.repository.row_mapper.OrderRowMapper;
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
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    private final String ORDER_SQL = """
            SELECT
            	id, user_id, user_name, certificate_with_tag_id, tag_name, 
            	certificate_name, description, duration, cost, create_date
            FROM
            	(SELECT tag_tb.id as cwt_id, tag_name, name as certificate_name, description, duration
            	FROM  certificate
            	JOIN ( SELECT certificate_with_tag.id, certificate_id, name as tag_name, tag_id
            			FROM certificate_with_tag
            			JOIN tag
            			WHERE certificate_with_tag.tag_id = tag.id
            		 ) tag_tb
            	WHERE tag_tb.certificate_id = certificate.id) cert_with_tag
            JOIN (SELECT user_order.id, user_id, name as user_name, certificate_with_tag_id, cost, create_date
            	FROM user_order JOIN user WHERE user.id = user_order.user_id) user_with_order
            WHERE cert_with_tag.cwt_id = user_with_order.certificate_with_tag_id
            """;

    public int create(Order order) {
        log.info("Repository. Create a new order.");
        final String SQL = "INSERT INTO user_order VALUES (default, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, order.getCost());
            ps.setTimestamp(2, Timestamp.from(order.getCreateDate()));
            ps.setInt(3, order.getUserId());
            ps.setInt(4, order.getCertificateWithTagId());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public List<Order> findAll() {
        log.info("Repository. Find all orders");
        return jdbcTemplate.query(ORDER_SQL,
                new OrderRowMapper());
    }

    public Optional<Order> findById(int id){
        log.info("Repository. Find order by id: " + id);
        return jdbcTemplate.query("SELECT * FROM user_order WHERE id=?",
                        new Object[]{id},
                        new OrderRowMapper())
                .stream()
                .findAny();
    }

    public boolean delete(int id) {
        log.info("Repository. Delete order by id: " + id);
        int result = jdbcTemplate.update("DELETE FROM user_order WHERE id=?", id);
        log.info("result of deleting " + result);
        return result == 1;
    }

    public List<Order> findByUser(String user) {
        log.info("Repository. Find all orders by user: " + user);
        String sql = "SELECT * FROM (" + ORDER_SQL + ") tab WHERE tab.user_name = ?";
        return jdbcTemplate.query(sql, new Object[]{user}, new OrderRowMapper());
    }
}

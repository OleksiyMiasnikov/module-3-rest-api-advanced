package com.epam.esm.repository;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.row_mapper.OrderRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
    private final String USER_WITH_MAX_TOTAL_COST_SQL = """
             SELECT user_id
             FROM (
                SELECT user_id, SUM(cost) AS sum_of_costs
                FROM user_order GROUP BY user_id) sum_of_cost_selection
             ORDER BY sum_of_costs DESC LIMIT 0, 1
            """;
    private final String MOST_WIDELY_USED_TAG = """
                SELECT tag_id
                FROM (
                    SELECT
                    tag_id,
                    COUNT(*) AS count_of_tags
                    FROM (
                        SELECT certificate_with_tag_id AS id 
                        FROM user_order 
                        WHERE user_id = ?) cwt_id_selection
                    JOIN certificate_with_tag
                    WHERE certificate_with_tag.id = cwt_id_selection.id
                    GROUP BY tag_id
                    LIMIT 1) tags_selection
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
        return jdbcTemplate.query("SELECT * FROM (" + ORDER_SQL + ") tab WHERE tab.id=?",
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

    public int findUserWithMaxTotalCost() {
        log.info("Repository. Find find user id with max total cost");
        return jdbcTemplate.queryForObject(USER_WITH_MAX_TOTAL_COST_SQL, Integer.class);
    }

    public int findMostlyUsedTag(int id) {
        log.info("Repository. Find find the mostly used tag of user with id: " + id);
        return jdbcTemplate.queryForObject(MOST_WIDELY_USED_TAG, new Object[]{id}, Integer.class);
    }
}

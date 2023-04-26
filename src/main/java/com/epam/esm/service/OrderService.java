package com.epam.esm.service;

import com.epam.esm.exception.ModuleException;
import com.epam.esm.model.DTO.order.CreateOrderRequest;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.entity.UserWithMaxTotalCost;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  A service to work with {@link Order}.
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository repo;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    /**
     * Creates new record of order.
     *
     * @param request - created order request
     * @return {@link Order} created order
     */
    @Transactional
    public Order create(CreateOrderRequest request) {
        log.info("Service. Create a new order");
        Order order = orderMapper.toOrder(request);

        int id = repo.save(order).getId();

        return repo.findById(id).orElse(null);
    }

    /**
     * Finds all orders.
     *
     * @return List of {@link Order} List of all orders.
     */
    public List<Order> findAll() {
        log.info("Service. Find all certificates with tags");
        return repo.findAll();
    }

    public List<Order> findByUser(String name) {
        log.info("Service. Find all orders by user: " + name);

        User user = userRepository.findByName(name).stream().findAny()
                .orElseThrow(() -> new ModuleException("Requested user is not found",
                "40001",
                HttpStatus.NOT_FOUND));

        return repo.findByUserId(user.getId());
    }

    public UserWithMaxTotalCost findUserWithMaxTotalCost(){
        log.info("Service. Find the most widely used tag of a user with the highest cost of all orders.");
        //TODO
//        UserWithMaxTotalCost userWithMaxTotalCost = repo.findUserWithMaxTotalCost()
//                .orElseThrow(() -> new ModuleException("Couldn't find the most widely used tag of a user with the highest cost of all orders.",
//                        "50011",
//                        HttpStatus.INTERNAL_SERVER_ERROR));
//
//        userWithMaxTotalCost.setTag_id(repo.findMostlyUsedTag(userWithMaxTotalCost.getUser_id()));
//
//        return userWithMaxTotalCost;
        return null;
    }

}

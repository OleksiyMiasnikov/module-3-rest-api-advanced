package com.epam.esm.service;

import com.epam.esm.exception.UserOrderNotFoundException;
import com.epam.esm.model.DTO.user_order.CreateUserOrderRequest;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.entity.UserOrder;
import com.epam.esm.model.entity.UserWithMaxTotalCost;
import com.epam.esm.repository.UserOrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

/**
 *  A service to work with {@link UserOrder}.
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class UserOrderService {

    private final UserOrderRepository repo;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    /**
     * Creates new record of order.
     *
     * @param request - created order request
     * @return {@link UserOrder} created order
     */
    @Transactional
    public UserOrder create(CreateUserOrderRequest request) {
        log.info("Create a new userOrder");
        UserOrder userOrder = orderMapper.toOrder(request);

        return repo.save(userOrder);
    }

    /**
     * Finds all orders.
     *
     * @return List of {@link UserOrder} List of all orders.
     */
    public Page<UserOrder> findAll(Pageable pageable) {
        log.info("Service. Find all certificates with tags");
        return repo.findAll(pageable);
    }

    public List<UserOrder> findByUser(String name) {
        log.info("Looking for all orders by user name: {}", name);

        User user = userRepository.findByName(name).stream().findAny()
                .orElseThrow(() -> new UserOrderNotFoundException(
                        "No order found by user name: " + name
                ));

        return repo.findByUserId(user.getId());
    }

    public UserWithMaxTotalCost findUserWithMaxTotalCost(){
        log.info("Service. Find the most widely used tag of a user with the highest cost of all orders.");
        UserWithMaxTotalCost userWithMaxTotalCost =
                repo.findUsersWithTotalCost().stream().max(Comparator.comparing(UserWithMaxTotalCost::getTotalCost))
                        .orElseThrow(() -> new UserOrderNotFoundException(
                                "Couldn't find the most widely used tag " +
                                "of a user with the highest cost of all orders."));

        userWithMaxTotalCost.setTagId(repo.findMostlyUsedTag(userWithMaxTotalCost.getUserId()).getTagId());
        return userWithMaxTotalCost;
    }

}
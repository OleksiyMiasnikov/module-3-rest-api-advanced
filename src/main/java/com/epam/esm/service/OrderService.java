package com.epam.esm.service;

import com.epam.esm.config.DateUtil;
import com.epam.esm.exception.ModuleException;
import com.epam.esm.model.DTO.SortingEntity;
import com.epam.esm.model.DTO.order.CreateOrderRequest;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.CertificateWithTagRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.mapper.CertificateMapper;
import com.epam.esm.service.mapper.OrderMapper;
import com.epam.esm.service.mapper.SortingEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 *  A service to work with {@link Order}.
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository repo;
    private final OrderMapper mapper;
    private final TagRepository tagRepository;

    /**
     * Creates new record of order.
     *
     * @param request - created order request
     * @return {@link Order} created order
     */
    @Transactional
    public Order create(CreateOrderRequest request) {
        log.info("Service. Create a new order");
        Order order = mapper.toOrder(request);

        int id = repo.create(order);

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

    public List<Order> findByUser(String user) {
        log.info("Service. Find all orders by user: " + user);
        return repo.findByUser(user);
    }

    public Tag findUserWithMaxTotalCost(){
        log.info("Service. Find user id with max total cost");
        int maxTotalCostUserId = repo.findUserWithMaxTotalCost();
        int mostlyUsedTagId = repo.findMostlyUsedTag(maxTotalCostUserId);
        return tagRepository.findById(mostlyUsedTagId)
                .orElseThrow(() -> new ModuleException("Couldn't find the most widely used tag of a user with the highest cost of all orders.",
                        "50011",
                        HttpStatus.INTERNAL_SERVER_ERROR));
    }

}

package com.epam.esm.service;

import com.epam.esm.config.DateUtil;
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
    private final OrderMapper mapper;

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
        log.info("Controller. Find all certificates with tags");
        return repo.findAll();
    }

}

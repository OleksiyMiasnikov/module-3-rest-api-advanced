package com.epam.esm.controller;

import com.epam.esm.model.DTO.order.CreateOrderRequest;
import com.epam.esm.model.DTO.order.OrderDTO;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.mapper.OrderMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;
    private final OrderMapper mapper;


    @PostMapping()
    public OrderDTO create(@Valid @RequestBody CreateOrderRequest request) {
        log.info("Controller. Create a new order");
        return mapper.toDTO(service.create(request));
    }

    @GetMapping()
    public List<OrderDTO> findAll() {
        log.info("Controller. Find all orders");
        return service.findAll().stream().map(mapper::toDTO).toList();
    }

    @GetMapping("/{user}")
    public List<OrderDTO> findByUser(@Valid @PathVariable("user") String user) {
        log.info("Controller. Find all orders by user: " + user);
        return service.findByUser(user).stream().map(mapper::toDTO).toList();
    }

}

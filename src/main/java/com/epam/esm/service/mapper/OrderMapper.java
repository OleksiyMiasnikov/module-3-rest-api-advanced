package com.epam.esm.service.mapper;

import com.epam.esm.config.DateUtil;
import com.epam.esm.exception.ModuleException;
import com.epam.esm.model.DTO.order.CreateOrderRequest;
import com.epam.esm.model.DTO.order.OrderDTO;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.Order;
import com.epam.esm.repository.CertificateWithTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Component
public class OrderMapper {

    private final CertificateWithTagRepository repo;
    private static final String PATTERN_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public Order toOrder(CreateOrderRequest request) {
        CertificateWithTag certificateWithTag
                = repo.findById(request.getCertificateWithTagId())
                          .orElseThrow(() -> new ModuleException("Requested certificate with tag is not found (id=" +
                                  request.getCertificateWithTagId() +
                                  ")",
                                "40471",
                                HttpStatus.NOT_FOUND));
        return Order.builder()
                .userId(request.getUserId())
                .CertificateWithTagId(request.getCertificateWithTagId())
                .cost(certificateWithTag.getPrice())
                .createDate(DateUtil.getDate())
                .build();
    }

    public OrderDTO toDTO(Order order) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
                .withZone(ZoneId.systemDefault());
        return OrderDTO.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .userName(order.getUserName())
                .CertificateWithTagId(order.getCertificateWithTagId())
                .tagName(order.getTagName())
                .certificateName(order.getCertificateName())
                .description(order.getDescription())
                .duration(order.getDuration())
                .cost(order.getCost())
                .createDate(formatter.format(order.getCreateDate()))
                .build();
    }
}

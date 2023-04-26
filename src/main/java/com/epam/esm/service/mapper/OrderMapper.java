package com.epam.esm.service.mapper;

import com.epam.esm.exception.ModuleException;
import com.epam.esm.model.DTO.certificate_with_tag.CertificateWithTagDTO;
import com.epam.esm.model.DTO.order.CreateOrderRequest;
import com.epam.esm.model.DTO.order.OrderDTO;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.UserOrder;
import com.epam.esm.model.entity.User;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.CertificateWithTagRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class OrderMapper {

    private final CertificateWithTagRepository certificateWithTagRepository;
    private final CertificateRepository certificateRepository;
    private final UserRepository userRepository;
    private final CertificateWithTagMapper mapper;
    private static final String PATTERN_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public UserOrder toOrder(CreateOrderRequest request) {
        CertificateWithTag certificateWithTag
                = certificateWithTagRepository.findById(request.getCertificateWithTagId())
                          .orElseThrow(() -> new ModuleException("Requested certificate with tag is not found (id=" +
                                  request.getCertificateWithTagId() +
                                  ")",
                                "40471",
                                HttpStatus.NOT_FOUND));

        Certificate certificate
                = certificateRepository.findById(certificateWithTag.getCertificateId())
                .orElseThrow(() -> new ModuleException("Requested certificate is not found (id=" +
                        certificateWithTag.getCertificateId() +
                        ")",
                        "40472",
                        HttpStatus.NOT_FOUND));

        return UserOrder.builder()
                .userId(request.getUserId())
                .CertificateWithTagId(request.getCertificateWithTagId())
                .cost(certificate.getPrice())
                .createDate(DateUtil.getDate())
                .build();
    }

    public OrderDTO toDTO(UserOrder userOrder) {
        Optional<User> userOptional = userRepository.findById(userOrder.getUserId());
        Optional<CertificateWithTag> certificateWithTagOptional = certificateWithTagRepository.findById(userOrder.getCertificateWithTagId());

        if (userOptional.isEmpty() || certificateWithTagOptional.isEmpty()) {
            throw new ModuleException("ups...", "50001", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        User user = userOptional.get();
        CertificateWithTagDTO certificateWithTag = mapper.toDTO(certificateWithTagOptional.get());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
                .withZone(ZoneId.systemDefault());

        return OrderDTO.builder()
                .id(userOrder.getId())
                .userName(user.getName())
                .CertificateWithTagId(userOrder.getCertificateWithTagId())
                .tagName(certificateWithTag.getTag())
                .certificateName(certificateWithTag.getName())
                .description(certificateWithTag.getDescription())
                .duration(certificateWithTag.getDuration())
                .cost(userOrder.getCost())
                .createDate(formatter.format(userOrder.getCreateDate()))
                .build();
    }
}

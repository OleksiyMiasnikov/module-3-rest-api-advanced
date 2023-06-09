package com.epam.esm.service;

import com.epam.esm.exception.CertificateWithTagNotFoundException;
import com.epam.esm.model.DTO.UserWithMaxTotalCostDTO;
import com.epam.esm.model.DTO.user_order.CreateUserOrderRequest;
import com.epam.esm.model.entity.*;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.UserOrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.mapper.OrderMapper;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserOrderServiceTest {

    @Mock
    private UserOrderRepository repo;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private UserOrderService subject;

    private  Pageable pageable;
    private Page<UserOrder> page;
    private UserOrder userOrder;
    private User user;

    @BeforeEach
    void setUp() {
        subject = new UserOrderService(repo, userRepository, orderMapper, tagRepository);
        userOrder = UserOrder.builder().build();
        pageable = Pageable.ofSize(3).withPage(0);
        page = new PageImpl<>(List.of(userOrder));
        user = User.builder().id(1).build();
    }

    @Test
    void create() {
        CreateUserOrderRequest request = CreateUserOrderRequest.builder().build();


        when(orderMapper.toOrder(request)).thenReturn(userOrder);
        when(repo.save(userOrder)).thenReturn(userOrder);

        assertThat(subject.create(request)).isEqualTo(userOrder);

        verify(orderMapper).toOrder(request);
        verify(repo).save(userOrder);
    }

    @Test
    void findAll() {
        when(repo.findAll(pageable)).thenReturn(page);

        Page<UserOrder> result = subject.findAll(pageable);

        assertThat(result.stream().count()).isEqualTo(1);
        assertThat(result).isEqualTo(new PageImpl<>(List.of(userOrder)));
    }

    @Test
    void findByUser() {
        String username = "user";

        when(userRepository.findByName(username)).thenReturn(List.of(user));
        when(repo.findByUserId(user.getId())).thenReturn(List.of(userOrder));

        List<UserOrder> result = subject.findByUser(username);
        assertThat(result).isEqualTo(List.of(userOrder));
    }

    @Test
    void findUserWithMaxTotalCost() {
        Tag tag = Tag.builder().id(2).build();
        Double totalCost = 25d;
        UserWithMaxTotalCost userWithMaxTotalCost = new UserWithMaxTotalCost() {
            @Override
            public Integer getUserId() {
                return user.getId();
            }

            @Override
            public Double getTotalCost() {
                return totalCost;
            }
        };

        MostlyUsedTagIdByUserId mostlyUsedTagIdByUserId = tag::getId;

        UserWithMaxTotalCostDTO expected = UserWithMaxTotalCostDTO.builder()
                .user(user.getName())
                .tag(tag.getName())
                .totalCost(totalCost)
                .build();

        when(repo.findUsersWithTotalCost()).thenReturn(userWithMaxTotalCost);
        when(repo.findMostlyUsedTag(1)).thenReturn(mostlyUsedTagIdByUserId);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(tagRepository.findById(2)).thenReturn(Optional.of(tag));

        UserWithMaxTotalCostDTO actual = subject.findUserWithMaxTotalCost();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(actual.getTotalCost()).isEqualTo(expected.getTotalCost());
        softAssertions.assertThat(actual.getUser()).isEqualTo(expected.getUser());
        softAssertions.assertThat(actual.getTag()).isEqualTo(expected.getTag());
        softAssertions.assertAll();

        verify(repo).findUsersWithTotalCost();
        verify(repo).findMostlyUsedTag(1);
        verify(userRepository).findById(1);
        verify(tagRepository).findById(2);
    }
}
package com.epam.esm.service;

import com.epam.esm.exception.CertificateWithTagNotFoundException;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.User;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.mapper.UserMapper;
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
class UserServiceTest {

    @Mock
    private UserRepository repo;
    @Mock
    private UserMapper mapper;
    private UserService subject;
    private int id;
    private User user;
    private User userBeforeSave;
    private  Pageable pageable;
    private Page<User> page;
    private String username;

    @BeforeEach
    void setUp() {
        subject = new UserService(repo);
        id = 1;
        user = User.builder().id(id).build();
        userBeforeSave = User.builder().build();
        pageable = Pageable.ofSize(3).withPage(0);
        page = new PageImpl<>(List.of(user));
        username = "user";
    }

    @Test
    void findById() {
        when(repo.findById(id)).thenReturn(Optional.of(user));
        User result = subject.findById(id);
        assertThat(result).isEqualTo(user);
    }

    @Test
    void findAll() {
        when(repo.findAll(pageable)).thenReturn(page);

        Page<User> result = subject.findAll(pageable);

        assertThat(result.stream().count()).isEqualTo(1);
        assertThat(result).isEqualTo(new PageImpl<>(List.of(user)));
    }

    @Test
    void findByName() {
        when(repo.findByName(username)).thenReturn(List.of(user));
        User result = subject.findByName(username).get(0);
        assertThat(result).isEqualTo(user);
    }

}
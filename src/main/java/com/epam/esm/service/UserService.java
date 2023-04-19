package com.epam.esm.service;

import com.epam.esm.exception.ModuleException;
import com.epam.esm.model.entity.User;
import com.epam.esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *  A service to work with {@link User}.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository repo;

    /**
     * Finds a {@link User} by its id.
     *
     * @param id user id
     * @return {@link User} user
     * @throws ModuleException if a user with a given id doesn't exist
     */
    public User findById(int id) {
        log.info("Service. Find user by id: " + id);
        Optional<User> result = repo.findById(id);
        return result.orElseThrow(() -> new ModuleException("Requested user is not found (id=" + id + ")",
                "40401",
                HttpStatus.NOT_FOUND));
    }

    /**
     * Finds all users.
     *
     * @return List of {@link User} List of all users from database
     */
    public List<User> findAll() {
        log.info("Service. Find all users");
        return repo.findAll();
    }
}

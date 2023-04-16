package com.epam.esm.services;

import com.epam.esm.exceptions.ModuleException;
import com.epam.esm.models.Tag;
import com.epam.esm.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *  A service to work with {@link Tag}.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TagService {

    private final TagRepository repo;

    /**
     * Creates a new tag.
     *
     * @param name - name of new tag
     * @return {@link Tag} created tag
     */
    public Tag create(String name) {
        log.info("Service. Create tag with name: " + name);
        int result = repo.create(name);
        return findById(result);
    }

    /**
     * Finds all tags.
     *
     * @return List of {@link Tag} List of all tags from database
     */
    public List<Tag> findAll() {
        log.info("Service. Find all tags");
        return repo.findAll();
    }

    /**
     * Finds a {@link Tag} by its id.
     *
     * @param id tag id
     * @return {@link Tag} tag
     * @throws ModuleException if a tag with a given id doesn't exist
     */
    public Tag findById(int id) {
        log.info("Service. Find tag by id: " + id);
        Optional<Tag> result = repo.findById(id);
        return result.orElseThrow(() -> new ModuleException("Requested tag is not found (id=" + id + ")", "40401"));
    }

    /**
     * Finds all {@link Tag} by name.
     *
     * @param name tag name
     * @return List {@link Tag} List of tags
     */
    public List<Tag> findByName(String name) {
        log.info("Service. Find tag by name: " + name);
        return repo.findByName(name);
    }

    /**
     * Removes a {@link Tag} by its id.
     *
     * @param id tag id
     * @return boolean result of removing tag with appropriate id
     */
    public boolean delete(int id) {
        log.info("Service. Delete tag by id: " + id);
        return repo.delete(id);
    }

}

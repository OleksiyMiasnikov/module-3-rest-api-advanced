package com.epam.esm.service;

import com.epam.esm.exception.ModuleException;
import com.epam.esm.model.DTO.tag.CreateTagRequest;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    private final TagMapper mapper;

    /**
     * Creates a new tag.
     *
     * @param createTagRequest - createTagRequest of new tag
     * @return {@link Tag} created tag
     */
    public Tag create(CreateTagRequest createTagRequest) {
        log.info("Service. Create a new tag.");
        Tag tag = mapper.toTag(createTagRequest);
        int result = repo.create(tag);
        return findById(result);
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
        return result.orElseThrow(() -> new ModuleException("Requested tag is not found (id=" + id + ")",
                "40401",
                HttpStatus.NOT_FOUND));
    }

    /**
     * Finds all {@link Tag} by name.
     * Returns all records if name is empty
     *
     * @param name tag name
     * @return List {@link Tag} List of tags
     */
    public List<Tag> findByName(String name) {
        log.info("Service. Find tag by name: " + name);
        return (name.isEmpty()) ? repo.findAll() : repo.findByName(name);
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

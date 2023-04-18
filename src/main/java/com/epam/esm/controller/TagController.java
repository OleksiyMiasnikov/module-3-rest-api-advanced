package com.epam.esm.controller;

import com.epam.esm.model.DTO.CreateTagRequest;
import com.epam.esm.model.DTO.TagDTO;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.mapper.TagMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController{

    private final TagService service;

    private final TagMapper tagMapper;

    @PostMapping()
    public TagDTO create(@Valid @RequestBody CreateTagRequest createTagRequest) {
        log.info("Controller. Create tag with name: " + createTagRequest.getName());
        return tagMapper.toDTO(service.create(createTagRequest));
    }

    @GetMapping("/{id}")
    public Tag findById(@PathVariable("id") int id) {
        log.info("Controller. Find tag by id: " + id);
        return service.findById(id);
    }

    @GetMapping()
    public List<Tag> findByName(@Valid @ModelAttribute ("name") String name) {
        log.info("Controller. Find tag by name: " + name);
        return service.findByName(name);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") int id) {
        log.info("Controller. Delete tag by id: " + id);
        return service.delete(id);
    }

}

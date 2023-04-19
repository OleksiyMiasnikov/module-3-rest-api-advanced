package com.epam.esm.controller;

import com.epam.esm.model.DTO.tag.CreateTagRequest;
import com.epam.esm.model.DTO.tag.TagDTO;
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

    private final TagMapper mapper;

    @PostMapping()
    public TagDTO create(@Valid @RequestBody CreateTagRequest request) {
        log.info("Controller. Create tag with name: " + request.getName());
        return mapper.toDTO(service.create(request));
    }

    @GetMapping("/{id}")
    public Tag findById(@PathVariable("id") int id) {
        log.info("Controller. Find tag by id: " + id);
        return service.findById(id);
    }

    @GetMapping()
    public List<TagDTO> findByName(@Valid @ModelAttribute ("name") String name) {
        log.info("Controller. Find tag by name: " + name);
        return service.findByName(name).stream().map(mapper::toDTO).toList();
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") int id) {
        log.info("Controller. Delete tag by id: " + id);
        return service.delete(id);
    }

}

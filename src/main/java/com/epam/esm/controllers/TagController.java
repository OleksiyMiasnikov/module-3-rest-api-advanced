package com.epam.esm.controllers;

import com.epam.esm.models.Tag;
import com.epam.esm.services.TagService;
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

    @PostMapping()
    public Tag create(@Valid @RequestBody Tag tag) {
        log.info("Controller. Create tag with name: " + tag.getName());
        return service.create(tag.getName());
    }

//    @GetMapping()
//    public List<Tag> findAll() {
//        log.info("Controller. Find all tags");
//        return service.findAll();
//    }

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

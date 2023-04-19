package com.epam.esm.controller;

import com.epam.esm.model.DTO.SortingEntity;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.service.CertificateWithTagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/certificates_with_tags")
@RequiredArgsConstructor
public class CertificateWithTagController{

    private final CertificateWithTagService service;


    @PostMapping()
    public CertificateWithTag create(@Valid @RequestBody CertificateWithTag certificateWithTag) {
        log.info("Controller. Create certificate with tag and name: "
                + certificateWithTag.getName());
        return service.create(certificateWithTag);
    }

    @GetMapping()
    public List<CertificateWithTag> findAll(
            @Valid @ModelAttribute("sort_by")SortingEntity sortingEntity) {
        log.info("Controller. Find all certificates with tags");
        return service.findAll(sortingEntity);
    }

    @GetMapping("/tag/{name}")
    public List<CertificateWithTag> findByTagName(@PathVariable("name") String name,
            @Valid @ModelAttribute("sort_by") SortingEntity sortingEntity) {
        log.info("Controller. Find all certificates with tag: " + name);
        return service.findByTagName(name, sortingEntity);
    }

    @GetMapping("/{id}")
    public CertificateWithTag findById(@PathVariable("id") int id) {
        log.info("Controller. Find certificate with tag by id: " + id);
        return service.findById(id);
    }

    @GetMapping("/search/{pattern}")
    public List<CertificateWithTag> findByPartOfNameOrDescription(
            @PathVariable("pattern") String pattern) {
        log.info("Controller. Find certificate by part of name or description");
        return service.findByPartOfNameOrDescription(pattern);
    }
}

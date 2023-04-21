package com.epam.esm.controller;

import com.epam.esm.model.DTO.SortingEntity;
import com.epam.esm.model.DTO.certificate_with_tag.CertificateWithTagDTO;
import com.epam.esm.model.DTO.certificate_with_tag.CertificateWithTagRequest;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.service.CertificateWithTagService;
import com.epam.esm.service.mapper.CertificateWithTagMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private final CertificateWithTagMapper mapper;


    @PostMapping()
    public CertificateWithTagDTO create(@Valid @RequestBody CertificateWithTagRequest request) {
        log.info("Controller. Create a new certificate with tag.");
        return mapper.toDTO(service.create(request));
    }

    @GetMapping()
    public List<CertificateWithTagDTO> findAll(@RequestParam("page") int page,
                                               @RequestParam("size") int size) {
        log.info("Controller. Find all certificates with tags");
        return service.findAllWithPage(page, size).stream().map(mapper::toDTO).toList();
    }

    @GetMapping("/tag/{name}")
    public List<CertificateWithTagDTO> findByTagName(@PathVariable("name") String name,
                                                     @RequestParam("page") int page,
                                                     @RequestParam("size") int size) {
        log.info("Controller. Find all certificates with tag: " + name);
        return service.findByTagNameWithPage(name, page, size).stream().map(mapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public CertificateWithTagDTO findById(@PathVariable("id") int id) {
        log.info("Controller. Find certificate with tag by id: " + id);
        return mapper.toDTO(service.findById(id));
    }

    @GetMapping("/search/{pattern}")
    public List<CertificateWithTagDTO> findByPartOfNameOrDescription(
            @PathVariable("pattern") String pattern) {
        log.info("Controller. Find certificate by part of name or description");
        return service.findByPartOfNameOrDescription(pattern).stream().map(mapper::toDTO).toList();
    }
}

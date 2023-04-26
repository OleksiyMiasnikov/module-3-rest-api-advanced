package com.epam.esm.controller;

import com.epam.esm.model.DTO.SortingEntity;
import com.epam.esm.model.DTO.certificate_with_tag.CertificateWithTagDTO;
import com.epam.esm.model.DTO.certificate_with_tag.CertificateWithTagRequest;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.service.CertificateWithTagService;
import com.epam.esm.service.mapper.CertificateWithTagMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/certificates_with_tags")
@RequiredArgsConstructor
public class CertificateWithTagController{

    private final CertificateWithTagService service;
    private final CertificateWithTagMapper mapper;


    @PostMapping()
    public CertificateWithTagDTO create(
            @Valid
            @RequestBody CertificateWithTagRequest request) {
        log.info("Controller. Create a new certificate with tag.");
        CertificateWithTagDTO createdDTO = mapper.toDTO(service.create(request));
        createdDTO.add(
                linkTo(methodOn(CertificateWithTagController.class)
                        .findById(createdDTO.getId()))
                        .withSelfRel());
        return createdDTO;
    }

    @GetMapping()
    public Page<CertificateWithTagDTO> findAll(Pageable pageable) {
        log.info("Controller. Find all certificates with tags");

        Page<CertificateWithTag> page = service.findAllWithPage(pageable);
        return page.map(mapper::toDTO);
    }

    @GetMapping("/tag/{name}")
    public List<CertificateWithTagDTO> findByTagNames(@ModelAttribute("sort_by") SortingEntity sortingEntity,
                                                     @PathVariable("name") List<String> list) {
        log.info("Controller. Find all certificates with tag");
        return service.findByTagNames(sortingEntity, list).stream().map(mapper::toDTO).toList();
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

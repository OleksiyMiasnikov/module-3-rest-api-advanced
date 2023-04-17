package com.epam.esm.controllers;

import com.epam.esm.exceptions.ModuleException;
import com.epam.esm.models.CertificateWithTag;
import com.epam.esm.services.CertificateWithTagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/certificates_with_tags")
@RequiredArgsConstructor
public class CertificateWithTagController{

    private final CertificateWithTagService service;

    @PostMapping()
    public CertificateWithTag create(@Valid @ModelAttribute CertificateWithTag certificateWithTag) {
        log.info("Controller. Create certificate with tag and name: "
                + certificateWithTag.getName());
        return service.create(certificateWithTag);
    }

    @GetMapping()
    public List<CertificateWithTag> findAll(@RequestParam("sort_by") String sortByName,
                                            @ModelAttribute("sort_by_date") String sortByDate) {
        log.info("Controller. Find all certificates with tags");
        return service.findAll(sortByName.toUpperCase(), sortByDate.toUpperCase());
    }

    @GetMapping("/tag/{name}")
    public List<CertificateWithTag> findByTagName(@PathVariable("name") String name,
                                                  @ModelAttribute("sort_by_name") String sortByName,
                                                  @ModelAttribute("sort_by_date") String sortByDate) {
        log.info("Controller. Find all certificates with tag: " + name);
        return service.findByTagName(name, sortByName.toUpperCase(), sortByDate.toUpperCase());
    }

    @GetMapping("/search/{pattern}")
    public List<CertificateWithTag> findByPartOfNameOrDescription(@PathVariable("pattern") String pattern) {
        log.info("Controller. Find certificate by part of name or description");
        return service.findByPartOfNameOrDescription(pattern);
    }
}

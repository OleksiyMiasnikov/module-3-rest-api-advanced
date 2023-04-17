package com.epam.esm.controller;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.service.CertificateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class CertificateController{

    private final CertificateService service;
    @PostMapping()
    public Certificate create(@Valid @RequestBody Certificate certificate) {
        log.info("Controller. Create certificate with name: " + certificate.getName());
                return service.create(certificate);
    }

    @GetMapping()
    public List<Certificate> findAll() {
        log.info("Controller. Find all certificates");
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Certificate findById(@PathVariable("id") int id) {
        log.info("Controller. Find certificate by id: " + id);
        return service.findById(id);
    }

    @PatchMapping("/{id}")
    public Certificate update(@PathVariable("id") int id,
                              @RequestBody Certificate certificate) {
        log.info("Controller. Update certificate by id: " + id);
        return service.update(id, certificate);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") int id) {
        log.info("Controller. Delete certificate by id: " + id);
        return service.delete(id);
    }

}

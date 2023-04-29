package com.epam.esm.controller;

import com.epam.esm.model.DTO.certificate.CertificateDTO;
import com.epam.esm.model.DTO.certificate.CreateCertificateRequest;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.mapper.CertificateMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class CertificateController{

    private final CertificateService service;
    private final CertificateMapper mapper;
    @PostMapping()
    public CertificateDTO create(
            @Valid
            @RequestBody CreateCertificateRequest request) {
        log.info("Controller. Create certificate with name: " + request.getName());
        return mapper.toDTO(service.create(request));
    }

    @GetMapping()
    public Page<CertificateDTO> findAll(Pageable pageable) {
        log.info("Controller. Find all certificates");
        Page<Certificate> page = service.findAll(pageable);
        return page.map(mapper::toDTO);
    }

    @GetMapping("/{id}")
    public CertificateDTO findById(@PathVariable("id") int id) {
        log.info("Controller. Find certificate by id: " + id);
        return mapper.toDTO(service.findById(id));
    }

    @PatchMapping("/{id}")
    public CertificateDTO update(@PathVariable("id") int id,
                              @RequestBody Certificate certificate) {
        log.info("Controller. Update certificate by id: " + id);
        return mapper.toDTO(service.update(id, certificate));
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") int id) {
        log.info("Controller. Delete certificate by id: " + id);
        return service.delete(id);
    }

}

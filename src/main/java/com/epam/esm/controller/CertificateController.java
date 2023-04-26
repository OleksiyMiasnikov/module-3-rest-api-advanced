package com.epam.esm.controller;

import com.epam.esm.model.DTO.PageDTO;
import com.epam.esm.model.DTO.certificate.CertificateDTO;
import com.epam.esm.model.DTO.certificate.CreateCertificateRequest;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.mapper.CertificateMapper;
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
    private final CertificateMapper mapper;
    @PostMapping()
    public CertificateDTO create(
            @Valid
            @RequestBody CreateCertificateRequest request) {
        log.info("Controller. Create certificate with name: " + request.getName());
        return mapper.toDTO(service.create(request));
    }

    @GetMapping()
    public PageDTO<CertificateDTO> findAll(@RequestParam("page") int page,
                                           @RequestParam("size") int size) {
        log.info("Controller. Find all certificates");
        List<CertificateDTO> list = service.findAll(page, size).stream().map(mapper::toDTO).toList();
        PageDTO<CertificateDTO> pageDTO = new PageDTO<>(list);
        //pageDTO.add(linkTo(methodOn(CertificateController.class).findAll(page - 1, size)).withSelfRel());
        //pageDTO.add(linkTo(methodOn(CertificateController.class).findAll(page + 1, size)).withSelfRel());
        return pageDTO;
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

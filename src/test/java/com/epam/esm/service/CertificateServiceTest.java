package com.epam.esm.service;

import com.epam.esm.exception.ModuleException;
import com.epam.esm.model.DTO.certificate.CreateCertificateRequest;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.service.mapper.CertificateMapper;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CertificateServiceTest {

    @Mock
    private CertificateRepository repo;
    @Mock
    private CertificateMapper mapper;

    private CertificateService subject;
    Certificate certificate;
    CreateCertificateRequest certificateRequest;
    int id = 1;
    @BeforeEach
    void setUp(){
        subject = new CertificateService(repo, mapper);
        certificate = Certificate.builder()
                .id(id)
                .name("certificate 1")
                .description("description of certificate 1")
                .price(15.5)
                .duration(5)
                .build();
        certificateRequest = CreateCertificateRequest.builder()
                .name("certificate 1")
                .description("description of certificate 1")
                .price(15.5)
                .duration(5)
                .build();
    }

    @Test
    void create() {
        when(repo.create(certificate)).thenReturn(id);
        when(repo.findById(id)).thenReturn(Optional.of(certificate));
        when(mapper.toCertificate(any(CreateCertificateRequest.class))).thenReturn(certificate);

        Certificate result = subject.create(certificateRequest);
        assertThat(result).isEqualTo(certificate);
    }

    @Test
    void findAll() {
        Certificate certificate2 = Certificate.builder()
                .id(++id)
                .name("certificate 2")
                .description("description of certificate 2")
                .price(25.5)
                .duration(15)
                .build();

        when(repo.sizeOfCertificate()).thenReturn(2);
        when(repo.findAll(1,3)).thenReturn(List.of(certificate, certificate2));


        List<Certificate> result = subject.findAll(1,3);
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).isEqualTo(List.of(certificate, certificate2));
    }

    @Test
    void findById() {
        when(repo.findById(id)).thenReturn(Optional.of(certificate));
        Certificate result = subject.findById(id);
        assertThat(result).isEqualTo(certificate);
    }
    @Test
    void findByIdThrowException() {
        int wrongId = 100;
        when(repo.findById(wrongId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> subject.findById(wrongId))
                .isInstanceOf(ModuleException.class)
                .hasMessageContaining("Requested certificate is not found (id=" + wrongId + ")");
    }

    @Test
    void updateSomeFields() {
        Certificate certificate2 = Certificate.builder()
                .id(++id)
                .name("certificate 2")
                .price(25.5)
                .build();
        Certificate certificate3 = Certificate.builder()
                .id(id)
                .name("certificate 2")
                .description("description of certificate 1")
                .price(25.5)
                .duration(5)
                .build();
        when(repo.findById(id)).thenReturn(Optional.of(certificate), Optional.of(certificate3));
        Certificate result = subject.update(id, certificate2);

        verify(repo).update(any(Integer.class), any(Certificate.class));

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(result.getId()).isEqualTo(certificate3.getId());
        softAssertions.assertThat(result.getName()).isEqualTo(certificate3.getName());
        softAssertions.assertThat(result.getDescription()).isEqualTo(certificate3.getDescription());
        softAssertions.assertThat(result.getPrice()).isEqualTo(certificate3.getPrice());
        softAssertions.assertThat(result.getDuration()).isEqualTo(certificate3.getDuration());
        softAssertions.assertAll();
    }

    @Test
    void updateAnotherFields() {
        Certificate certificate2 = Certificate.builder()
                .id(++id)
                .description("description of certificate 2")
                .duration(15)
                .build();
        Certificate certificate3 = Certificate.builder()
                .id(id)
                .name("certificate 1")
                .description("description of certificate 2")
                .price(15.5)
                .duration(15)
                .build();
        when(repo.findById(id)).thenReturn(Optional.of(certificate), Optional.of(certificate3));

        Certificate result = subject.update(id, certificate2);

        verify(repo).update(any(Integer.class), any(Certificate.class));

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(result.getId()).isEqualTo(certificate3.getId());
        softAssertions.assertThat(result.getName()).isEqualTo(certificate3.getName());
        softAssertions.assertThat(result.getDescription()).isEqualTo(certificate3.getDescription());
        softAssertions.assertThat(result.getPrice()).isEqualTo(certificate3.getPrice());
        softAssertions.assertThat(result.getDuration()).isEqualTo(certificate3.getDuration());
        softAssertions.assertAll();
    }

    @Test
    void delete() {
        when(repo.delete(id)).thenReturn(true);
        assertThat(subject.delete(id)).isTrue();
    }
}
package com.epam.esm.services;

import com.epam.esm.models.CertificateWithTag;
import com.epam.esm.models.Tag;
import com.epam.esm.repositories.CertificateRepository;
import com.epam.esm.repositories.CertificateWithTagRepository;
import com.epam.esm.repositories.TagRepository;
import com.epam.esm.validators.SortingValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CertificateWithTagServiceTest {
    @Mock
    CertificateWithTagRepository repo;
    @Mock
    TagRepository tagRepo;
    @Mock
    CertificateRepository certificateRepo;
    @Mock
    SortingValidator sortingValidator;
    CertificateWithTagService subject;

    CertificateWithTag certificateWithTag;

    @BeforeEach
    void setUp() {
        subject = new CertificateWithTagService(repo,
                tagRepo,
                certificateRepo,
                sortingValidator);
        certificateWithTag = CertificateWithTag.builder()
                .tag("tag_name")
                .name("certificate 1")
                .description("description of certificate 1")
                .price(100.50)
                .duration(7)
                .createDate("2023-04-13 14:56:06")
                .lastUpdateDate("2023-04-13 14:56:06")
                .build();
    }

    @Test
    void create() {
        Tag tag = Tag.builder()
                .id(1)
                .name("tag_name")
                .build();
        int certificateId = 1;
        when(tagRepo.findByName(certificateWithTag.getTag())).thenReturn(List.of(tag));
        when(certificateRepo.create(any())).thenReturn(certificateId);
        when(repo.findByTagIdAndCertificateId(tag.getId(), certificateId))
                .thenReturn(Optional.of(certificateWithTag));
        assertThat(subject.create(certificateWithTag)).isEqualTo(certificateWithTag);
        verify(repo).create(tag.getId(), certificateId);
    }

    @Test
    void createWithNewTag() {
        int certificateId = 1;
        int tagId = 5;
        when(tagRepo.findByName(certificateWithTag.getTag())).thenReturn(List.of());
        when(certificateRepo.create(any())).thenReturn(certificateId);
        when(tagRepo.create(certificateWithTag.getTag())).thenReturn(tagId);
        when(repo.findByTagIdAndCertificateId(tagId, certificateId))
                .thenReturn(Optional.of(certificateWithTag));
        assertThat(subject.create(certificateWithTag)).isEqualTo(certificateWithTag);
        //verify(validator).validate(certificateWithTag);
        verify(repo).create(tagId, certificateId);
    }

    @Test
    void findAll() {
        when(repo.findAll("ASC", "ASC")).thenReturn(List.of(certificateWithTag));
        List<CertificateWithTag> result = subject.findAll("ASC", "ASC");
        assertThat(result.size()).isEqualTo(1);
        assertThat(result).isEqualTo(List.of(certificateWithTag));
    }

    @Test
    void findByTagName() {
        when(repo.findByTagName("tag 1", "ASC", "ASC"))
                .thenReturn(List.of(certificateWithTag));
        List<CertificateWithTag> result = subject
                .findByTagName("tag 1", "ASC", "ASC");
        assertThat(result).isEqualTo(List.of(certificateWithTag));
        verify(sortingValidator, times(2)).validate(any());
    }

    @Test
    void findByPartOfNameOrDescription() {
        when(repo.findByPartOfNameOrDescription(any()))
                .thenReturn(List.of(certificateWithTag));
        assertThat(subject.findByPartOfNameOrDescription(any()))
                .isEqualTo(List.of(certificateWithTag));
    }
}
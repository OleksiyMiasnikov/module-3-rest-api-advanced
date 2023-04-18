package com.epam.esm.service;

import com.epam.esm.model.DTO.SortingEntity;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.CertificateWithTagRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.mapper.CertificateMapper;
import com.epam.esm.service.mapper.SortingEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CertificateWithTagServiceTest {
    @Mock
    CertificateWithTagRepository repo;
    @Mock
    TagRepository tagRepo;
    @Mock
    CertificateRepository certificateRepo;
    @Mock
    CertificateWithTagService subject;
    @Mock
    CertificateMapper mapper;
    @Mock
    SortingEntityMapper sortingEntityMapper;

    CertificateWithTag certificateWithTag;

    @BeforeEach
    void setUp() {
        subject = new CertificateWithTagService(repo,
                tagRepo,
                certificateRepo,
                mapper,
                sortingEntityMapper);
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
        Certificate certificate = new Certificate();

        when(mapper.toCertificate(any(CertificateWithTag.class))).thenReturn(certificate);
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
        Certificate certificate = new Certificate();

        when(mapper.toCertificate(any(CertificateWithTag.class))).thenReturn(certificate);
        when(tagRepo.findByName(certificateWithTag.getTag())).thenReturn(List.of());
        when(certificateRepo.create(any())).thenReturn(certificateId);
        when(tagRepo.create(any(Tag.class))).thenReturn(tagId);
        when(repo.findByTagIdAndCertificateId(tagId, certificateId))
                .thenReturn(Optional.of(certificateWithTag));

        assertThat(subject.create(certificateWithTag)).isEqualTo(certificateWithTag);

        verify(repo).create(tagId, certificateId);
    }

    @Test
    void findAll() {
        SortingEntity sortingEntity = new SortingEntity("name", "ASC");

        when(sortingEntityMapper.toSortBy(any(SortingEntity.class)))
                .thenReturn(sortingEntity);
        when(repo.findAll(sortingEntity)).thenReturn(List.of(certificateWithTag));

        List<CertificateWithTag> result = subject.findAll(sortingEntity);

        assertThat(result.size()).isEqualTo(1);
        assertThat(result).isEqualTo(List.of(certificateWithTag));
    }

    @Test
    void findByTagName() {
        SortingEntity sortingEntity = new SortingEntity("name", "ASC");

        when(sortingEntityMapper.toSortBy(any(SortingEntity.class)))
                .thenReturn(sortingEntity);
        when(repo.findByTagName("tag 1", sortingEntity))
                .thenReturn(List.of(certificateWithTag));

        List<CertificateWithTag> result = subject
                .findByTagName("tag 1", sortingEntity);
        assertThat(result).isEqualTo(List.of(certificateWithTag));
    }

    @Test
    void findByPartOfNameOrDescription() {
        when(repo.findByPartOfNameOrDescription(any()))
                .thenReturn(List.of(certificateWithTag));
        assertThat(subject.findByPartOfNameOrDescription(any()))
                .isEqualTo(List.of(certificateWithTag));
    }
}
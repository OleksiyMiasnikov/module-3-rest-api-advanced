package com.epam.esm.service;

import com.epam.esm.config.DateUtil;
import com.epam.esm.model.DTO.CreateTagRequest;
import com.epam.esm.model.DTO.SortingEntity;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.CertificateWithTagRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.mapper.CertificateMapper;
import com.epam.esm.service.mapper.SortingEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  A service to work with {@link CertificateWithTag}.
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class CertificateWithTagService{
    private final CertificateWithTagRepository repo;
    private final TagRepository tagRepo;
    private final CertificateRepository certificateRepo;
    private final CertificateMapper certificateMapper;
    private final SortingEntityMapper sortingEntityMapper;

    /**
     * Creates new record of certificate with tag.
     * If tag doesn't exist, it will be created
     *
     * @param certificateWithTag - certificate with tag
     * @return {@link CertificateWithTag} created tag
     */
    @Transactional
    public CertificateWithTag create(CertificateWithTag certificateWithTag) {
        log.info("Service. Create certificate with tag and name: " + certificateWithTag.getName());

        // if tag exists in the database, tagId get from database
        // else a new tag will be created with new tagId
        int tagId;
        List<Tag> tagList = tagRepo.findByName(certificateWithTag.getTag());
        if (tagList.size() == 0) {
            Tag tag = Tag.builder()
                    .name(certificateWithTag.getTag())
                    .build();
            tagId = tagRepo.create(tag);
        } else {
            tagId = tagList.get(0).getId();
        }

        Certificate certificate = certificateMapper.toCertificate(certificateWithTag);
        certificate.setCreateDate(DateUtil.getDate());
        certificate.setLastUpdateDate(DateUtil.getDate());
        int certificateId = certificateRepo.create(certificate);
        repo.create(tagId, certificateId);

        return repo.findByTagIdAndCertificateId(tagId, certificateId).orElse(null);
    }

    /**
     * Finds all certificates with tags.
     * Result will be sorted by name and created date
     *
     * @param sortingEntity sorting criterion
     * @return List of {@link CertificateWithTag} List of all certificates with tags from database
     */
    public List<CertificateWithTag> findAll(SortingEntity sortingEntity) {
        log.info("Controller. Find all certificates with tags");
        return repo.findAll(sortingEntityMapper.toSortBy(sortingEntity));
    }

    /**
     * Finds all certificates by tag name.
     * Result will be sorted by name and created date
     *
     * @param name name of tag
     * @param sortingEntity sorting criterion
     * @return List of {@link CertificateWithTag} List of all certificates with appropriate tag
     */
    public List<CertificateWithTag> findByTagName(String name, SortingEntity sortingEntity) {
        log.info("Controller. Find all certificates with tag: " + name);
        return repo.findByTagName(name, sortingEntityMapper.toSortBy(sortingEntity));
    }

    /**
     * Finds all certificates by part of name/description.
     *
     * @param pattern part of name/description
     * @return List of {@link CertificateWithTag} List of all appropriate certificates with tags
     */
    public List<CertificateWithTag> findByPartOfNameOrDescription(String pattern) {
        log.info("Controller. Find certificate by part of name or description.");
        return repo.findByPartOfNameOrDescription(pattern);
    }
}

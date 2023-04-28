package com.epam.esm.service;

import com.epam.esm.exception.ModuleException;
import com.epam.esm.model.DTO.certificate_with_tag.CertificateWithTagRequest;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.CertificateWithTagRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.mapper.CertificateMapper;
import com.epam.esm.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    /**
     * Creates new record of certificate with tag.
     * If tag doesn't exist, it will be created
     *
     * @param request - created certificate with tag request
     * @return {@link CertificateWithTag} created tag
     */
    @Transactional
    public CertificateWithTag create(CertificateWithTagRequest request) {
        //log.info("Service. Create a new certificate with tag.");

        // if tag exists in the database, tagId get from database
        // else a new tag will be created with new tagId
        int tagId;
        List<Tag> tagList = tagRepo.findByName(request.getTag());
        if (tagList.size() == 0) {
            Tag tag = Tag.builder()
                    .name(request.getTag())
                    .build();
            tagId = tagRepo.save(tag).getId();
        } else {
            tagId = tagList.get(0).getId();
        }

        Certificate certificate = certificateMapper.toCertificate(request);
        certificate.setCreateDate(DateUtil.getDate());
        certificate.setLastUpdateDate(DateUtil.getDate());

        int certificateId = certificateRepo.save(certificate).getId();
        CertificateWithTag certificateWithTag = CertificateWithTag.builder()
                .tagId(tagId)
                .certificateId(certificateId)
                .build();
        return repo.save(certificateWithTag);
    }

    /**
     * Finds all certificates with tags by page.
     * Result will be selected by page and size.
     *
     * @param pageable page parameters
     * @return List of {@link CertificateWithTag} List of all certificates with tags from database
     */
    public Page<CertificateWithTag> findAll(Pageable pageable) {
        log.info("Controller. Find all certificates with tags");
        return repo.findAll(pageable);
    }


    /**
     * Finds all certificates by tags name.
     *
     * @param pageable page parameters
     * @param tagList list with tags
     * @return List of {@link CertificateWithTag} List of all certificates with appropriate tag
     */
    public Page<CertificateWithTag> findByTagNames(Pageable pageable, List<String> tagList) {
        log.info("Controller. Find all certificates with tag");
        List<Integer> tagIds = tagList.stream()
                .map(t -> tagRepo.findByName(t).get(0).getId())
                .toList();
        return repo.findByTagIds(tagIds, pageable);
    }

    /**
     * Finds all certificates by part of name/description.
     *
     * @param pattern part of name/description
     * @return List of {@link CertificateWithTag} List of all appropriate certificates with tags
     */
    public List<CertificateWithTag> findByPartOfNameOrDescription(String pattern) {
        log.info("Service. Find certificate by part of name or description.");

        Set<Certificate> set = new HashSet<>(certificateRepo.findByNameContaining(pattern));
        set.addAll(certificateRepo.findByDescriptionContaining(pattern));

        List<Integer> listOfCertificateId = new ArrayList<>(set.stream().map(Certificate::getId).toList());

        return repo.findByCertificateId(listOfCertificateId);
    }

    /**
     * Finds a {@link CertificateWithTag} by its id.
     *
     * @param id certificate with tag id
     * @return {@link CertificateWithTag} CertificateWithTag
     * @throws ModuleException if a certificate with tag with a given id doesn't exist
     */
    public CertificateWithTag findById(int id) {
        log.info("Service. Find certificate with tag by id: " + id);

        return repo.findById(id)
                .orElseThrow(() -> new ModuleException("Requested certificate is not found (id=" + id + ")",
                        "40411",
                        HttpStatus.NOT_FOUND));
    }
}

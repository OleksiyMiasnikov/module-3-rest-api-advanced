package com.epam.esm.service;

import com.epam.esm.exception.ModuleException;
import com.epam.esm.model.DTO.SortingEntity;
import com.epam.esm.model.DTO.certificate_with_tag.CertificateWithTagRequest;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.CertificateWithTagRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.mapper.CertificateMapper;
import com.epam.esm.service.mapper.CertificateWithTagMapper;
import com.epam.esm.service.mapper.SortingEntityMapper;
import com.epam.esm.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final CertificateWithTagMapper mapper;
    private final TagRepository tagRepo;
    private final CertificateRepository certificateRepo;
    private final CertificateMapper certificateMapper;
    private final SortingEntityMapper sortingEntityMapper;

    /**
     * Creates new record of certificate with tag.
     * If tag doesn't exist, it will be created
     *
     * @param request - created certificate with tag request
     * @return {@link CertificateWithTag} created tag
     */
    @Transactional
    public CertificateWithTag create(CertificateWithTagRequest request) {
        log.info("Service. Create a new certificate with tag.");

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
     * @param page number of page
     * @param size number of rows on the page
     * @return List of {@link CertificateWithTag} List of all certificates with tags from database
     */
    public List<CertificateWithTag> findAllWithPage(int page, int size) {
        log.info("Controller. Find all certificates with tags");
        // TODO
//        if (repo.sizeOfCertificateWithTag() <= (page - 1) * size) {
//            throw new ModuleException("There are no fields for page " + page + " with size " + size,
//                    "40491",
//                    HttpStatus.BAD_REQUEST);
//        }
        if (page <= 0 || size <= 0) {
            throw new ModuleException("Parameters page and size must be more then 0",
                    "40492",
                    HttpStatus.BAD_REQUEST);
        }
        return repo.findAll();//repo.findAllWithPage(page, size);
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
        return repo.findAll();
        // TODO
        //return repo.findAll(sortingEntityMapper.toSortBy(sortingEntity));
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
        // TODO
        //return repo.findByTagName(name, sortingEntityMapper.toSortBy(sortingEntity));
        return null;
    }

    public List<CertificateWithTag> findByTagNames(SortingEntity sortingEntity, List<String> tagList) {
        log.info("Controller. Find all certificates with tag");

        List<CertificateWithTag> list = new LinkedList<>();
        tagList.forEach(
                l -> {
                    Optional<Tag> tag = tagRepo.findByName(l).stream().findAny();
                    if (tag.isEmpty()) {
                        return;
                    }
                    list.addAll(repo.findByTagId(tag.get().getId()));
                }
        );
        return list;
    }

    /**
     * Finds all certificates by tag name.
     * Result will be sorted by name and created date
     *
     * @param name name of tag
     * @param page number of page
     * @param size number of rows on the page
     * @return List of {@link CertificateWithTag} List of all certificates with appropriate tag
     */
    public List<CertificateWithTag> findByTagNameWithPage(String name, int page, int size) {
        log.info("Controller. Find all certificates with tag: " + name);
        // TODO
        //return repo.findByTagNameWithPage(name, page, size);
        return null;
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

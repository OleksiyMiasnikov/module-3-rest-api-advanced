package com.epam.esm.service;

import com.epam.esm.util.DateUtil;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        CertificateWithTag certificateWithTag = mapper.toCertificateWithTag(request);

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
     * Finds all certificates with tags by page.
     * Result will be selected by page and size.
     *
     * @param page number of page
     * @param size number of rows on the page
     * @return List of {@link CertificateWithTag} List of all certificates with tags from database
     */
    public List<CertificateWithTag> findAllWithPage(int page, int size) {
        log.info("Controller. Find all certificates with tags");
        if (repo.sizeOfCertificateWithTag() <= (page - 1) * size) {
            throw new ModuleException("There are no fields for page " + page + " with size " + size,
                    "40491",
                    HttpStatus.BAD_REQUEST);
        }
        if (page <= 0 || size <= 0) {
            throw new ModuleException("Parameters page and size must be more then 0",
                    "40492",
                    HttpStatus.BAD_REQUEST);
        }
        return repo.findAllWithPage(page, size);
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

    public List<CertificateWithTag> findByTagName(SortingEntity sortingEntity, List<String> list) {
        log.info("Controller. Find all certificates with tag");
        //String pattern = list.stream().collect(Collectors.joining(" OR "));
        //String[] names = list.toArray(new String[0]);
        return repo.findByTagName(list, sortingEntityMapper.toSortBy(sortingEntity));
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
        return repo.findByTagNameWithPage(name, page, size);
    }

    /**
     * Finds all certificates by part of name/description.
     *
     * @param pattern part of name/description
     * @return List of {@link CertificateWithTag} List of all appropriate certificates with tags
     */
    public List<CertificateWithTag> findByPartOfNameOrDescription(String pattern) {
        log.info("Service. Find certificate by part of name or description.");
        return repo.findByPartOfNameOrDescription(pattern);
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

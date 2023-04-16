package com.epam.esm.services;

import com.epam.esm.exceptions.ModuleException;
import com.epam.esm.models.Certificate;
import com.epam.esm.models.CertificateWithTag;
import com.epam.esm.models.Tag;
import com.epam.esm.repositories.CertificateRepository;
import com.epam.esm.repositories.CertificateWithTagRepository;
import com.epam.esm.repositories.TagRepository;
import com.epam.esm.validators.SortingValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

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
    private final SortingValidator sortingValidator;

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
            tagId = tagRepo.create(certificateWithTag.getTag());
        } else {
            tagId = tagList.get(0).getId();
        }

        int certificateId = certificateRepo.create(Certificate.mapper(certificateWithTag));
        repo.create(tagId, certificateId);

        return repo.findByTagIdAndCertificateId(tagId, certificateId).orElse(null);
    }

    /**
     * Finds all certificates with tags.
     * Result will be sorted by name and created date
     *
     * @param sortByDate sorting criterion of date
     * @param sortByName sorting criterion of name
     * @return List of {@link CertificateWithTag} List of all certificates with tags from database
     */
    public List<CertificateWithTag> findAll(String sortByName, String sortByDate) {
        log.info("Controller. Find all certificates with tags");
        // validate sorting parameters

        sortingValidator.validate(sortByDate);
        sortingValidator.validate(sortByName);

        return repo.findAll(sortByName, sortByDate);
    }

    /**
     * Finds all certificates by tag name.
     * Result will be sorted by name and created date
     *
     * @param name name of tag
     * @param sortByDate sorting criterion of date
     * @param sortByName sorting criterion of name
     * @return List of {@link CertificateWithTag} List of all certificates with appropriate tag
     */
    public List<CertificateWithTag> findByTagName(String name, String sortByName, String sortByDate) {
        log.info("Controller. Find all certificates with tag: " + name);
        // validate sorting parameters
        sortingValidator.validate(sortByDate);
        sortingValidator.validate(sortByName);

        return repo.findByTagName(name, sortByName, sortByDate);
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

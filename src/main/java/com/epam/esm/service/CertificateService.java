package com.epam.esm.service;

import com.epam.esm.exception.ModuleException;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.repositorie.CertificateRepository;
import com.epam.esm.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  A service to work with {@link Certificate}.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CertificateService {

    private final CertificateRepository repo;

    /**
     * Creates a new tag.
     *
     * @param certificate - create certificate
     * @return {@link Certificate} created certificate
     */
    public Certificate create(Certificate certificate) {
        log.info("Service. Create certificate with name: " + certificate.getName());

        certificate.setCreateDate(DateUtil.getDate());
        certificate.setLastUpdateDate(DateUtil.getDate());

        int result = repo.create(certificate);

        return findById(result);
    }

    /**
     * Finds all certificates.
     *
     * @return List of {@link Certificate} List of all certificates from database
     */
    public List<Certificate> findAll() {
        log.info("Service. Find all certificates");

        return repo.findAll();
    }

    /**
     * Finds a {@link Certificate} by its id.
     *
     * @param id certificate id
     * @return {@link Certificate} certificate
     * @throws ModuleException if a certificate with a given id doesn't exist
     */
    public Certificate findById(int id) {
        log.info("Service. Find certificate by id: " + id);

        return repo.findById(id)
                .orElseThrow(() -> new ModuleException("Requested certificate is not found (id=" + id + ")",
                                        "40411",
                                        HttpStatus.NOT_FOUND));
    }

    /**
     * Updates a {@link Certificate} by its id.
     * Updates only fields, that present(not null)
     *
     * @param id certificate id
     * @param certificate fields to update
     * @return {@link Certificate} updated certificate
     */
    @Transactional
    public Certificate update(int id, Certificate certificate) {
        log.info("Service. Update certificate by id: " + id);

        Certificate oldCertificate = findById(id);
        if (certificate.getName() == null) {
            certificate.setName(oldCertificate.getName());
        }
        if (certificate.getDescription() == null) {
            certificate.setDescription(oldCertificate.getDescription());
        }
        if (certificate.getPrice() == null) {
            certificate.setPrice(oldCertificate.getPrice());
        }
        if (certificate.getDuration() == null) {
            certificate.setDuration(oldCertificate.getDuration());
        }

        certificate.setLastUpdateDate(DateUtil.getDate());

        repo.update(id, certificate);

        return findById(id);
    }

    /**
     * Removes a {@link Certificate} by its id.
     *
     * @param id certificate id
     * @return boolean result of removing certificate with appropriate id
     */
    public boolean delete(int id) {
        log.info("Service. Delete certificate by id: " + id);

        return repo.delete(id);
    }

}

package com.epam.esm.repository;

import com.epam.esm.model.entity.CertificateWithTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CertificateWithTagRepository extends JpaRepository<CertificateWithTag, Integer> {
    List<CertificateWithTag> findByTagId(int tagId);

    @Query("FROM CertificateWithTag WHERE certificateId in :ids" )
    List<CertificateWithTag> findByCertificateId(@Param("ids") List<Integer> listOfCertificateId);
}

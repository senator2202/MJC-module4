package com.epam.esm.model.repository;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface GiftCertificateRepository extends
        JpaRepository<GiftCertificate, Long>,
        JpaSpecificationExecutor<GiftCertificate> {

    List<GiftCertificate> findByNameContaining(String name, Pageable pageable);

    List<GiftCertificate> findByDescriptionContaining(String description, Pageable pageable);

    List<GiftCertificate> findByTagsIn(List<Tag> tags, Pageable pageable);
}

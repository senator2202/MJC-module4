package com.epam.esm.model.repository;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The repository for Gift certificate entity.
 */
@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>,
        QuerydslPredicateExecutor<GiftCertificate> {

    /**
     * Find gift certificates that contain definite tag
     *
     * @param tag the tag
     * @return the list
     */
    List<GiftCertificate> findGiftCertificateByTagsContaining(Tag tag);
}

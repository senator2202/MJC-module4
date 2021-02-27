package com.epam.esm.model.repository;

import com.epam.esm.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The repository for Tag entity.
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long>,
        JpaSpecificationExecutor<GiftCertificateRepository> {

    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<Tag> findByName(String name);
}

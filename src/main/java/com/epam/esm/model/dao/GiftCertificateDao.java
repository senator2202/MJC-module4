package com.epam.esm.model.dao;

import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface provides additional DB operation for GiftCertificate entity
 */
public interface GiftCertificateDao extends BaseDao<GiftCertificate> {

    /**
     * Find all list.
     *
     * @param name        the name
     * @param description the description
     * @param tagNames    the tag names
     * @param pageable    the Pageable object
     * @return the list
     */
    List<GiftCertificate> findAll(String name, String description, String[] tagNames, Pageable pageable);
}

package com.epam.esm.service;

import com.epam.esm.model.dto.GiftCertificateDTO;

import java.util.List;

/**
 * Interface provides some additional operations on GiftCertificate entity
 */
public interface GiftCertificateService extends BaseService<GiftCertificateDTO> {

    /**
     * Find all list.
     *
     * @param name        the name
     * @param description the description
     * @param tagNames    the tag names
     * @param sortType    the sort type
     * @param direction   the direction
     * @param page        the page number
     * @param size        the page size
     * @return the list
     */
    List<GiftCertificateDTO> findAll(String name, String description, String tagNames, String sortType,
                                     String direction, Integer page, Integer size);

}

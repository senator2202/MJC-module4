package com.epam.esm.service;

import com.epam.esm.model.dto.TagDTO;

import java.util.List;

/**
 * Interface provides additional operation on Tag entity
 */
public interface TagService extends BaseService<TagDTO> {

    /**
     * Find all tags, according to page number and size of page.
     *
     * @param page the page number
     * @param size the size of page
     * @return the list
     */
    List<TagDTO> findAll(Integer page, Integer size);
}

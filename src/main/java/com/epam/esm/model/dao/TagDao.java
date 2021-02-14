package com.epam.esm.model.dao;

import com.epam.esm.model.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Interface provides additional DB operations for Tag entity
 */
public interface TagDao extends BaseDao<Tag> {

    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<Tag> findByName(String name);


    /**
     * Find all tags.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<Tag> findAll(Pageable pageable);
}

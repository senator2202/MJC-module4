package com.epam.esm.model.dao;

import com.epam.esm.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Interface provides DB operations for User entity
 */
public interface UserDao {

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<User> findById(long id);

    /**
     * Find all users.
     *
     * @param pageable  the Pageable object
     * @return the list
     */
    Page<User> findAll(Pageable pageable);

    /**
     * User id with highest order sum long.
     *
     * @return the long
     */
    Long userIdWithHighestOrderSum();
}

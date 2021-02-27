package com.epam.esm.service;

import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.dto.UserRegistrationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interface provides operation on Order entity.
 */
public interface UserService {

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<UserDTO> findById(long id);

    /**
     * Find all list.
     *
     * @param page the page number
     * @param size the of page
     * @return the list
     */
    List<UserDTO> findAll(Integer page, Integer size);

    /**
     * Add user method.
     *
     * @param data user registration data
     * @return the user dto
     */
    UserDTO add(UserRegistrationDTO data);
}

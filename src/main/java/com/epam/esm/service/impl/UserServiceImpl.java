package com.epam.esm.service.impl;

import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ObjectConverter;
import com.epam.esm.util.PageableProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type User service.
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserDTO> findById(long id) {
        Optional<User> byId = userRepository.findById(id);
        return byId.map(ObjectConverter::toUserDTO);
    }

    @Override
    public List<UserDTO> findAll(Integer page, Integer size) {
        Pageable pageable = PageableProvider.pageable(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        return ObjectConverter.toUserDTOs(userPage.getContent());
    }
}

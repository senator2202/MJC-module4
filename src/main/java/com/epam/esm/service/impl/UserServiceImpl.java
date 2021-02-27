package com.epam.esm.service.impl;

import com.epam.esm.controller.error_handler.ProjectError;
import com.epam.esm.exception.ExceptionProvider;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.dto.UserRegistrationDTO;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.RoleRepository;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ObjectConverter;
import com.epam.esm.util.PageableProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The type User service.
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private ExceptionProvider exceptionProvider;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setExceptionProvider(ExceptionProvider exceptionProvider) {
        this.exceptionProvider = exceptionProvider;
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

    @Override
    @Transactional
    public UserDTO add(UserRegistrationDTO data) {
        if (userRepository.existsByUsername(data.getUsername())) {
            throw exceptionProvider.userNameAlreadyExistsException(ProjectError.USER_NAME_ALREADY_EXISTS);
        }
        User user = new User();
        user.setName(data.getName());
        user.setUsername(data.getUsername());
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        user.setRole(roleRepository.findUserRole());
        return ObjectConverter.toUserDTO(userRepository.save(user));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public User add(User user) {
        user.setRole(roleRepository.findUserRole());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}

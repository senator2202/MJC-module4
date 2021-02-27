package com.epam.esm.service.impl;

import com.epam.esm.controller.error_handler.ProjectError;
import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.exception.ExceptionProvider;
import com.epam.esm.exception.UserNameAlreadyExistsException;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.Role;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.RoleRepository;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Test class for UserServiceImpl methods
 */
class UserServiceImplTest {

    @InjectMocks
    private final UserService userService = new UserServiceImpl();
    @Mock
    private UserRepository userRepository;
    @Mock
    private Page<User> userPage;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private ExceptionProvider exceptionProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByIdExisting() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(StaticDataProvider.USER));
        Optional<UserDTO> actual = userService.findById(1L);
        Optional<UserDTO> expected = Optional.of(StaticDataProvider.USER_DTO);
        assertEquals(actual, expected);
    }

    @Test
    void findByIdNotExisting() {
        when(userRepository.findById(11111L)).thenReturn(Optional.empty());
        Optional<UserDTO> actual = userService.findById(11111L);
        Optional<UserDTO> expected = Optional.empty();
        assertEquals(actual, expected);
    }

    @Test
    void findAll() {
        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);
        when(userPage.getContent()).thenReturn(Collections.nCopies(5, StaticDataProvider.USER));
        List<UserDTO> actual = userService.findAll(0, 5);
        List<UserDTO> expected = Collections.nCopies(5, StaticDataProvider.USER_DTO);
        assertEquals(actual, expected);
    }

    @Test
    void addUsernameDoesNotExist() {
        when(userRepository.existsByUsername("alex")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encoded");
        when(roleRepository.findUserRole()).thenReturn(new Role());
        when(userRepository.save(any(User.class))).thenReturn(StaticDataProvider.USER);
        assertEquals(StaticDataProvider.USER_DTO, userService.add(StaticDataProvider.USER_REGISTRATION_DTO));
    }

    @Test
    void addUsernameExist() {
        when(userRepository.existsByUsername("alex")).thenReturn(true);
        when(exceptionProvider.userNameAlreadyExistsException(any(ProjectError.class))).thenReturn(
                new UserNameAlreadyExistsException(null, 0)
        );
        assertThrows(UserNameAlreadyExistsException.class,
                () -> userService.add(StaticDataProvider.USER_REGISTRATION_DTO));
    }

    @Test
    void findByUsernameExist() {
        Optional<User> result = Optional.of(StaticDataProvider.USER);
        when(userRepository.findByUsername("Alex")).thenReturn(result);
        assertEquals(result, userService.findByUsername("Alex"));
    }

    @Test
    void findByUsernameNotExist() {
        when(userRepository.findByUsername("Alexaaaaaaaaa")).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), userService.findByUsername("Alexaaaaaaaaa"));
    }

    @Test
    void add() {
        when(roleRepository.findUserRole()).thenReturn(StaticDataProvider.USER_ROLE);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(StaticDataProvider.USER);
        assertEquals(StaticDataProvider.USER, userService.add(StaticDataProvider.USER));
    }
}
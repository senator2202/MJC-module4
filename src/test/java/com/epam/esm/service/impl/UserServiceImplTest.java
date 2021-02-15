package com.epam.esm.service.impl;

import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Page<User> userPage;

    @InjectMocks
    private final UserService service = new UserServiceImpl(userRepository);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByIdExisting() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(StaticDataProvider.USER));
        Optional<UserDTO> actual = service.findById(1L);
        Optional<UserDTO> expected = Optional.of(StaticDataProvider.USER_DTO);
        assertEquals(actual, expected);
    }

    @Test
    void findByIdNotExisting() {
        when(userRepository.findById(11111L)).thenReturn(Optional.empty());
        Optional<UserDTO> actual = service.findById(11111L);
        Optional<UserDTO> expected = Optional.empty();
        assertEquals(actual, expected);
    }

    @Test
    void findAll() {
        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);
        when(userPage.get()).thenReturn(Collections.nCopies(5, StaticDataProvider.USER).stream());
        List<UserDTO> actual = service.findAll(0, 5);
        List<UserDTO> expected = Collections.nCopies(5, StaticDataProvider.USER_DTO);
        assertEquals(actual, expected);
    }
}
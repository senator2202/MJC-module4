package com.epam.esm.service.impl;

import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UserDetailsServiceImplTest {

    @InjectMocks
    private final UserDetailsService userDetailsService = new UserDetailsServiceImpl();
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsernameExists() {
        when(userRepository.findByUsername("alex")).thenReturn(Optional.of(StaticDataProvider.USER));
        assertEquals(userDetailsService.loadUserByUsername("alex"), StaticDataProvider.SECURITY_USER);
    }

    @Test
    void loadUserByUsernameDoesNotExists() {
        when(userRepository.findByUsername("alex")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("alex"));
    }
}
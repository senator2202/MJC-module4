package com.epam.esm.model.repository;

import com.epam.esm.app.SpringBootRestApplication;
import com.epam.esm.model.entity.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for UserRepository methods, using in application
 */
@SpringBootTest(classes = SpringBootRestApplication.class)
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    static Stream<Arguments> argsFindById() {
        return Stream.of(
                Arguments.of(1L, true),
                Arguments.of(999L, false)
        );
    }

    static Stream<Arguments> argsFindAll() {
        return Stream.of(
                Arguments.of(Pageable.unpaged(), 6),
                Arguments.of(PageRequest.of(0, 3), 3),
                Arguments.of(PageRequest.of(2, 2), 2),
                Arguments.of(PageRequest.of(10, 10), 0)
        );
    }

    @ParameterizedTest
    @MethodSource("argsFindById")
    void testFindById(Long id, boolean result) {
        assertEquals(result, userRepository.findById(id).isPresent());
    }

    @ParameterizedTest
    @MethodSource("argsFindAll")
    void findAll(Pageable pageable, int actualSize) {
        Page<User> allTags = userRepository.findAll(pageable);
        assertEquals(actualSize, allTags.getNumberOfElements());
    }
}
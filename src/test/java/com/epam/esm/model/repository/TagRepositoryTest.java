package com.epam.esm.model.repository;

import com.epam.esm.app.SpringBootRestApplication;
import com.epam.esm.model.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = SpringBootRestApplication.class)
@Transactional
class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    static Stream<Arguments> argsFindById() {
        return Stream.of(
                Arguments.of(1L, true),
                Arguments.of(999L, false)
        );
    }

    static Stream<Arguments> argsFindAll() {
        return Stream.of(
                Arguments.of(Pageable.unpaged(), 16),
                Arguments.of(PageRequest.of(0, 10), 10),
                Arguments.of(PageRequest.of(1, 10), 6),
                Arguments.of(PageRequest.of(10, 10), 0)
        );
    }

    static Stream<Arguments> argsFindByName() {
        return Stream.of(
                Arguments.of("Активность", true),
                Arguments.of("SomethingElse", false)
        );
    }

    @ParameterizedTest
    @MethodSource("argsFindById")
    void findById(Long id, boolean result) {
        assertEquals(result, tagRepository.findById(id).isPresent());
    }

    @ParameterizedTest
    @MethodSource("argsFindAll")
    void findAll(Pageable pageable, int actualSize) {
        Page<Tag> allTags = tagRepository.findAll(pageable);
        assertEquals(actualSize, allTags.getNumberOfElements());
    }

    @Test
    void add() {
        tagRepository.save(new Tag("NewTag"));
        Page<Tag> allTags = tagRepository.findAll(Pageable.unpaged());
        assertEquals(17L, allTags.getTotalElements());
    }

    @Test
    void update() {
        Tag updated = tagRepository.save(new Tag(1L, "Пассивность"));
        Optional<Tag> optional = tagRepository.findById(1L);
        assertTrue(optional.isPresent() && optional.get().equals(updated));
    }

    @Test
    void deleteByIdExisting() {
        tagRepository.deleteById(1L);
        assertFalse(tagRepository.existsById(1L));
    }

    @Test
    void deleteByIdNotExisting() {
        assertThrows(EmptyResultDataAccessException.class, () -> tagRepository.deleteById(999L));
    }

    @ParameterizedTest
    @MethodSource("argsFindByName")
    void findByName(String name, boolean result) {
        Optional<Tag> optional = tagRepository.findByName(name);
        assertEquals(result, optional.isPresent());
    }
}
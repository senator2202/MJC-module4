package com.epam.esm.service.impl;

import com.epam.esm.app.SpringBootRestApplication;
import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.GiftCertificateRepository;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Test class for TagServiceImpl methods
 */
@SpringBootTest(classes = SpringBootRestApplication.class)
class TagServiceImplTest {

    @InjectMocks
    private final TagService service = new TagServiceImpl();
    @Mock
    private TagRepository tagRepository;
    @Mock
    private Page<Tag> tagPage;
    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByIdExisting() {
        when(tagRepository.findById(1L)).thenReturn(Optional.of(StaticDataProvider.TAG));
        Optional<TagDTO> actual = service.findById(1L);
        Optional<TagDTO> expected = Optional.of(StaticDataProvider.TAG_DTO);
        assertEquals(actual, expected);
    }

    @Test
    void findByIdNotExisting() {
        when(tagRepository.findById(11111L)).thenReturn(Optional.empty());
        Optional<TagDTO> actual = service.findById(11111L);
        Optional<TagDTO> expected = Optional.empty();
        assertEquals(actual, expected);
    }

    @Test
    void findAll() {
        when(tagRepository.findAll(any(Pageable.class))).thenReturn(tagPage);
        when(tagPage.getContent()).thenReturn(Collections.nCopies(5, StaticDataProvider.TAG));
        List<TagDTO> actual = service.findAll(0, 5);
        List<TagDTO> expected = Collections.nCopies(5, StaticDataProvider.TAG_DTO);
        assertEquals(actual, expected);
    }

    @Test
    void addExisting() {
        when(tagRepository.findByName("Вязание")).thenReturn(Optional.of(StaticDataProvider.TAG));
        TagDTO actual = service.add(StaticDataProvider.TAG_DTO);
        TagDTO expected = StaticDataProvider.TAG_DTO;
        assertEquals(actual, expected);
    }

    @Test
    void addNotExisting() {
        when(tagRepository.findByName("Baseball")).thenReturn(Optional.empty());
        when(tagRepository.save(StaticDataProvider.ADDING_TAG)).thenReturn(StaticDataProvider.ADDED_TAG);
        TagDTO actual = service.add(StaticDataProvider.ADDING_TAG_DTO);
        TagDTO expected = StaticDataProvider.ADDED_TAG_DTO;
        assertEquals(actual, expected);
    }

    @Test
    void updateExisting() {
        when(tagRepository.findById(1L)).thenReturn(Optional.of(StaticDataProvider.TAG));
        when(tagRepository.save(StaticDataProvider.TAG)).thenReturn(StaticDataProvider.TAG);
        Optional<TagDTO> actual = service.update(StaticDataProvider.TAG_DTO);
        Optional<TagDTO> expected = Optional.of(StaticDataProvider.TAG_DTO);
        assertEquals(actual, expected);
    }

    @Test
    void updateNotExisting() {
        when(tagRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<TagDTO> actual = service.update(StaticDataProvider.ADDED_TAG_DTO);
        Optional<TagDTO> expected = Optional.empty();
        assertEquals(actual, expected);
    }

    @Test
    void deleteTrue() {
        when(tagRepository.findById(1L)).thenReturn(Optional.of(StaticDataProvider.TAG));
        when(giftCertificateRepository.findGiftCertificateByTagsContaining(any(Tag.class))).thenReturn(new ArrayList<>());
        doNothing().when(tagRepository).deleteById(1L);
        assertTrue(service.delete(1L));
    }

    @Test
    void deleteFalse() {
        when(tagRepository.existsById(1L)).thenReturn(false);
        assertFalse(service.delete(11111L));
    }
}
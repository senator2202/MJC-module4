package com.epam.esm.service.impl;

import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.repository.GiftCertificateRepository;
import com.epam.esm.model.repository.OrderRepository;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.querydsl.core.types.Predicate;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class GiftCertificateServiceImplTest {

    @InjectMocks
    private final GiftCertificateService giftCertificateService = new GiftCertificateServiceImpl();
    @Mock
    private GiftCertificateRepository giftCertificateRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private Page<GiftCertificate> page;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByIdExisting() {
        when(giftCertificateRepository.findById(1L)).thenReturn(Optional.of(StaticDataProvider.GIFT_CERTIFICATE));
        Optional<GiftCertificateDTO> actual = giftCertificateService.findById(1L);
        Optional<GiftCertificateDTO> expected = Optional.of(StaticDataProvider.GIFT_CERTIFICATE_DTO);
        assertEquals(actual, expected);
    }

    @Test
    void findByIdNotExisting() {
        when(giftCertificateRepository.findById(11111L)).thenReturn(Optional.empty());
        Optional<GiftCertificateDTO> actual = giftCertificateService.findById(11111L);
        Optional<GiftCertificateDTO> expected = Optional.empty();
        assertEquals(actual, expected);
    }

    @Test
    void findAll() {
        when(giftCertificateRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(page);
        when(page.getContent()).thenReturn(Collections.nCopies(5, StaticDataProvider.GIFT_CERTIFICATE));
        List<GiftCertificateDTO> actual =
                giftCertificateService.findAll("Certificate", "Good certificate", "TagA,TagB", "price", "asc", 0, 5);
        List<GiftCertificateDTO> expected = Collections.nCopies(5, StaticDataProvider.GIFT_CERTIFICATE_DTO);
        assertEquals(actual, expected);
    }

    @Test
    void add() {
        when(tagRepository.findByName("Вязание")).thenReturn(Optional.of(StaticDataProvider.TAG));
        when(giftCertificateRepository.save(StaticDataProvider.GIFT_CERTIFICATE))
                .thenReturn(StaticDataProvider.GIFT_CERTIFICATE);
        GiftCertificateDTO actual = giftCertificateService.add(StaticDataProvider.GIFT_CERTIFICATE_DTO);
        GiftCertificateDTO expected = StaticDataProvider.GIFT_CERTIFICATE_DTO;
        assertEquals(actual, expected);
    }

    @Test
    void updateExisting() {
        when(giftCertificateRepository.findById(1L)).thenReturn(Optional.of(StaticDataProvider.GIFT_CERTIFICATE));
        when(giftCertificateRepository.save(StaticDataProvider.GIFT_CERTIFICATE))
                .thenReturn(StaticDataProvider.GIFT_CERTIFICATE);
        Optional<GiftCertificateDTO> actual = giftCertificateService.update(StaticDataProvider.GIFT_CERTIFICATE_DTO);
        Optional<GiftCertificateDTO> expected = Optional.of(StaticDataProvider.GIFT_CERTIFICATE_DTO);
        assertEquals(actual, expected);
    }

    @Test
    void deleteTrue() {
        doNothing().when(giftCertificateRepository).deleteById(1L);
        doNothing().when(orderRepository).deleteOrdersByGiftCertificateId(1L);
        when(giftCertificateRepository.existsById(1L)).thenReturn(true);
        assertTrue(giftCertificateService.delete(1L));
    }

    @Test
    void deleteFalse() {
        doNothing().when(giftCertificateRepository).deleteById(1L);
        when(giftCertificateRepository.existsById(1L)).thenReturn(false);
        assertFalse(giftCertificateService.delete(11111L));
    }
}
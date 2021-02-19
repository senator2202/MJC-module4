package com.epam.esm.model.repository;

import com.epam.esm.app.SpringBootRestApplication;
import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.entity.GiftCertificate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.epam.esm.util.GiftCertificateExpressionProvider.getBooleanExpression;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = SpringBootRestApplication.class)
@Transactional
class GiftCertificateRepositoryTest {

    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    static Stream<Arguments> argsFindById() {
        return Stream.of(
                Arguments.of(1L, true),
                Arguments.of(999L, false)
        );
    }

    static Stream<Arguments> argsFindAll() {
        return Stream.of(
                Arguments.of(getBooleanExpression("тату", null, null), Pageable.unpaged(), 2,
                        Comparator.comparing(GiftCertificate::getId)),
                Arguments.of(getBooleanExpression(null, "бесплатн", null), Pageable.unpaged(), 5,
                        Comparator.comparing(GiftCertificate::getId)),
                Arguments.of(getBooleanExpression(null, null, "Активность"), Pageable.unpaged(), 3,
                        Comparator.comparing(GiftCertificate::getId)),
                Arguments.of(getBooleanExpression(null, null, "Активность,Отдых"), Pageable.unpaged(), 2,
                        Comparator.comparing(GiftCertificate::getId)),
                Arguments.of(getBooleanExpression(null, null, null),
                        PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.ASC, "price")), 13,
                        Comparator.comparing(GiftCertificate::getPrice)),
                Arguments.of(getBooleanExpression(null, null, null),
                        PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.DESC, "price")), 13,
                        Comparator.comparing(GiftCertificate::getPrice).reversed()),
                Arguments.of(getBooleanExpression(null, null, null),
                        PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.ASC, "duration")), 13,
                        Comparator.comparing(GiftCertificate::getDuration)),
                Arguments.of(getBooleanExpression(null, null, null),
                        PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.DESC, "duration")), 13,
                        Comparator.comparing(GiftCertificate::getDuration).reversed()),
                Arguments.of(getBooleanExpression(null, null, null),
                        PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.ASC, "createDate")), 13,
                        Comparator.comparing(GiftCertificate::getCreateDate)),
                Arguments.of(getBooleanExpression(null, null, null),
                        PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.DESC, "createDate")), 13,
                        Comparator.comparing(GiftCertificate::getCreateDate).reversed()),
                Arguments.of(getBooleanExpression(null, null, null),
                        PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.ASC, "lastUpdateDate")), 13,
                        Comparator.comparing(GiftCertificate::getLastUpdateDate)),
                Arguments.of(getBooleanExpression(null, null, null),
                        PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.DESC, "lastUpdateDate")), 13,
                        Comparator.comparing(GiftCertificate::getLastUpdateDate).reversed()),
                Arguments.of(getBooleanExpression(null, null, null),
                        PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "price")), 10,
                        Comparator.comparing(GiftCertificate::getPrice)),
                Arguments.of(getBooleanExpression(null, null, null),
                        PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "price")), 3,
                        Comparator.comparing(GiftCertificate::getPrice)),
                Arguments.of(getBooleanExpression(null, null, null),
                        PageRequest.of(20, 10, Sort.by(Sort.Direction.ASC, "price")), 0,
                        Comparator.comparing(GiftCertificate::getPrice)),
                Arguments.of(getBooleanExpression("на", null, "Активность,Отдых"), Pageable.unpaged(), 2,
                        Comparator.comparing(GiftCertificate::getId))
        );
    }

    @ParameterizedTest
    @MethodSource("argsFindById")
    void findById(Long id, boolean result) {
        Optional<GiftCertificate> optional = giftCertificateRepository.findById(id);
        assertEquals(result, optional.isPresent());
    }

    @ParameterizedTest
    @MethodSource("argsFindAll")
    void findAll(BooleanExpression expression, Pageable pageable, int size, Comparator<GiftCertificate> comparator) {
        List<GiftCertificate> allTags = giftCertificateRepository.findAll(expression, pageable).getContent();
        List<GiftCertificate> sorted = new ArrayList<>(allTags);
        sorted.sort(comparator);
        assertTrue(allTags.size() == size && allTags.equals(sorted));
    }

    @Test
    void deleteById() {
        giftCertificateRepository.deleteById(1L);
        assertFalse(giftCertificateRepository.existsById(1L));
    }

    @Test
    void deleteByIdNotExisting() {
        assertThrows(EmptyResultDataAccessException.class, () -> giftCertificateRepository.deleteById(999L));
    }

    @Test
    void add() {
        GiftCertificate created = giftCertificateRepository.save(StaticDataProvider.ADDING_GIFT_CERTIFICATE);
        Optional<GiftCertificate> optional = giftCertificateRepository.findById(created.getId());
        assertTrue(optional.isPresent() && optional.get().equals(created));
    }

    @Test
    void update() {
        GiftCertificate updated = giftCertificateRepository.save(StaticDataProvider.UPDATING_GIFT_CERTIFICATE);
        Optional<GiftCertificate> optional = giftCertificateRepository.findById(1L);
        assertTrue(optional.isPresent() && optional.get().equals(updated));
    }
}
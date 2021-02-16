package com.epam.esm.util;

import com.epam.esm.model.entity.QGiftCertificate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GiftCertificateExpressionProviderTest {

    static Stream<Arguments> argsGetBooleanExpression() {
        QGiftCertificate certificate = QGiftCertificate.giftCertificate;
        return Stream.of(
                Arguments.of(null, null, null, certificate.isNotNull()),
                Arguments.of("tattoo", null, null,
                        certificate.isNotNull().and(certificate.name.containsIgnoreCase("tattoo"))),
                Arguments.of(null, "free", null,
                        certificate.isNotNull().and(certificate.description.containsIgnoreCase("free"))),
                Arguments.of(null, null, "Activity",
                        certificate.isNotNull().and(certificate.tags.any().name.eq("Activity"))),
                Arguments.of(null, null, "Activity,Relax",
                        certificate.isNotNull()
                                .and(certificate.tags.any().name.eq("Activity"))
                                .and(certificate.tags.any().name.eq("Relax"))),
                Arguments.of(null, "free", "Activity,Relax",
                        certificate.isNotNull()
                                .and(certificate.description.containsIgnoreCase("free"))
                                .and(certificate.tags.any().name.eq("Activity"))
                                .and(certificate.tags.any().name.eq("Relax"))),
                Arguments.of("tattoo", "free", "Activity,Relax",
                        certificate.isNotNull()
                                .and(certificate.name.containsIgnoreCase("tattoo"))
                                .and(certificate.description.containsIgnoreCase("free"))
                                .and(certificate.tags.any().name.eq("Activity"))
                                .and(certificate.tags.any().name.eq("Relax")))
        );
    }

    @ParameterizedTest
    @MethodSource("argsGetBooleanExpression")
    void getBooleanExpression(String name, String description, String tagNames, BooleanExpression result) {
        assertEquals(result, GiftCertificateExpressionProvider.getBooleanExpression(name, description, tagNames));
    }
}
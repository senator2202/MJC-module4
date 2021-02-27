package com.epam.esm.validator;

import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.UserRegistrationDTO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for GiftEntityValidator methods, validating request parameters
 */
class GiftEntityValidatorTest {

    static Stream<Arguments> correctIdArgs() {
        return Stream.of(
                Arguments.of(true, new long[]{1L}),
                Arguments.of(false, new long[]{0L}),
                Arguments.of(false, new long[]{-22L}),
                Arguments.of(true, new long[]{22L, 155L}),
                Arguments.of(false, new long[]{22L, -22L})
        );
    }

    static Stream<Arguments> correctTagNameArgs() {
        return Stream.of(
                Arguments.of("Book", true),
                Arguments.of("", false),
                Arguments.of("111111111111111111111111111111111111111111111111111111111111111111111111111111111", false)
        );
    }

    static Stream<Arguments> correctCertificateDescriptionArgs() {
        return Stream.of(
                Arguments.of("Description", true),
                Arguments.of("", false),
                Arguments.of("111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                        "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                        "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                        "11111111111111111111111111111111111111111", false)
        );
    }

    static Stream<Arguments> correctTagNamesArgs() {
        return Stream.of(
                Arguments.of("Book,English", true),
                Arguments.of("English,111111111111111111111111111111111111111111111111111111111111111111111", false)
        );
    }

    static Stream<Arguments> correctOptionalParametersArgs() {
        return Stream.of(
                Arguments.of("Name", "Description", "Активность,Отдых", "price", "desc", 25, 10, true),
                Arguments.of(null, null, null, null, null, null, null, true),
                Arguments.of("", "Description", "Активность,Отдых", "price", "desc", 25, 10, false),
                Arguments.of("Name", "", "Активность,Отдых", "price", "desc", 25, 10, false),
                Arguments.of("Name", "Description", "", "price", "desc", 25, 10, false),
                Arguments.of("Name", "Description", "Активность,Отдых", "banana", "desc", 25, 10, false),
                Arguments.of("Name", "Description", "Активность,Отдых", "price", "up", 25, 10, false),
                Arguments.of("Name", "Description", "Активность,Отдых", "price", "desc", -25, 10, false),
                Arguments.of("Name", "Description", "Активность,Отдых", "price", "desc", 25, -10, false)
        );
    }

    static Stream<Arguments> correctTagArgs() {
        return Stream.of(
                Arguments.of(new TagDTO(null, "Name"), true),
                Arguments.of(new TagDTO(null, ""), false),
                Arguments.of(new TagDTO(1L, "Name111111111111111111111111111111111111111111111111111111111111"), false),
                Arguments.of(null, false)
        );
    }

    static Stream<Arguments> correctGiftCertificateArgs() {
        return Stream.of(
                Arguments.of(
                        new GiftCertificateDTO(1L, "Name", "Description", BigDecimal.valueOf(100), 30,
                                "2021-01-13T11:03Z", "2021-01-13T11:03Z", StaticDataProvider.TAG_DTO_LIST), true),
                Arguments.of(
                        new GiftCertificateDTO(null, "Name", null, null, null, null, null, null), true),
                Arguments.of(
                        new GiftCertificateDTO(null, null, null, null, null, null, null, null), false),
                Arguments.of(
                        new GiftCertificateDTO(null, "", null, null, null, null, null, null), false),
                Arguments.of(
                        new GiftCertificateDTO(null, "Name", "", null, null, null, null, null), false),
                Arguments.of(
                        new GiftCertificateDTO(null, "Name", null, BigDecimal.valueOf(-99.99),
                                null, null, null, null), false),
                Arguments.of(
                        new GiftCertificateDTO(null, "Name", null, null, -30, null, null, null), false),
                Arguments.of(
                        new GiftCertificateDTO(null, "Name", null, null, null, null, null, List.of(new TagDTO())),
                        false)
        );
    }

    static Stream<Arguments> correctOptionalCertificateNameArgs() {
        return Stream.of(
                Arguments.of("Name", true),
                Arguments.of(null, true),
                Arguments.of("", false),
                Arguments.of("Name111111111111111111111111111111111111111111111111111111111111111111111111", false)
        );
    }

    static Stream<Arguments> correctGiftCertificateOptionalArgs() {
        return Stream.of(
                Arguments.of(new GiftCertificateDTO(null, null, null, null, null, null, null, null), true),
                Arguments.of(new GiftCertificateDTO(1L, null, null, null, null, null, null, null), true),
                Arguments.of(new GiftCertificateDTO(-1L, null, null, null, null, null, null, null), true),
                Arguments.of(new GiftCertificateDTO(null, "name", null, null, null, null, null, null), true),
                Arguments.of(new GiftCertificateDTO(null, "1111111111111111111111111111111111111111" +
                        "11111111111111111111111111111111", null, null, null, null, null, null), false),
                Arguments.of(new GiftCertificateDTO(null, "", null, null, null, null, null, null), false),
                Arguments.of(new GiftCertificateDTO(null, null, "description", null, null, null, null, null), true),
                Arguments.of(new GiftCertificateDTO(null, null, "", null, null, null, null, null), false),
                Arguments.of(new GiftCertificateDTO(null, null, "11111111111111111111111111111111111111111111111111" +
                        "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                        "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                        "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                        "11111111111111111111111111", null, null, null, null, null), false),
                Arguments.of(new GiftCertificateDTO(null, null, null, BigDecimal.valueOf(1.25),
                        null, null, null, null), true),
                Arguments.of(new GiftCertificateDTO(null, null, null, BigDecimal.valueOf(0),
                        null, null, null, null), false),
                Arguments.of(new GiftCertificateDTO(null, null, null, BigDecimal.valueOf(-25.25),
                        null, null, null, null), false),
                Arguments.of(new GiftCertificateDTO(null, null, null, null, 25, null, null, null), true),
                Arguments.of(new GiftCertificateDTO(null, null, null, null, -25, null, null, null), false),
                Arguments.of(new GiftCertificateDTO(null, null, null, null, 0, null, null, null), false),
                Arguments.of(new GiftCertificateDTO(null, null, null, null, null, null, null,
                        List.of(new TagDTO(null, "NewTag"))), true),
                Arguments.of(new GiftCertificateDTO(null, null, null, null, null, null, null,
                        List.of(new TagDTO(null, null))), false),
                Arguments.of(new GiftCertificateDTO(null, null, null, null, null, null, null,
                        List.of(new TagDTO(null, ""))), false),
                Arguments.of(new GiftCertificateDTO(null, null, null, null, null, null, null,
                        List.of(new TagDTO(null, "NewTag"), new TagDTO(null, null))), false),
                Arguments.of(new GiftCertificateDTO(null, null, null, null, null, null, null,
                        List.of(new TagDTO(null, "111111111111111111111111111111111111111111111111111111111" +
                                "111111111111111111111111111111111111111111111111111111"))), false)
        );
    }

    static Stream<Arguments> correctUserRegistrationDataArgs() {
        return Stream.of(
                Arguments.of(new UserRegistrationDTO("Alexey Kharitonov", "alex", "password", "password"), true),
                Arguments.of(new UserRegistrationDTO(null, null, null, null), false),
                Arguments.of(new UserRegistrationDTO("Alexey Kharitonov1111111111111111111111111111111111111111" +
                        "11111111111111111111111111111111111111111", null, null, null), false),
                Arguments.of(new UserRegistrationDTO("Alexey Kharitonov", null, null, null), false),
                Arguments.of(new UserRegistrationDTO("Alexey Kharitonov", "alex", null, null), false),
                Arguments.of(new UserRegistrationDTO("Alexey Kharitonov", "alex,", null, null), false),
                Arguments.of(new UserRegistrationDTO("Alexey Kharitonov", "alex 22", null, null), false),
                Arguments.of(new UserRegistrationDTO("Alexey Kharitonov", "alex", "password", null), false),
                Arguments.of(new UserRegistrationDTO("Alexey Kharitonov", "alex", "password", ""), false),
                Arguments.of(new UserRegistrationDTO("Alexey Kharitonov", "alex", "password", "not match"), false)
        );
    }

    @ParameterizedTest
    @MethodSource("correctIdArgs")
    void correctId(boolean result, long... ids) {
        assertEquals(GiftEntityValidator.correctId(ids), result);
    }

    @ParameterizedTest
    @MethodSource("correctTagNameArgs")
    void correctTagName(String tagName, boolean result) {
        assertEquals(GiftEntityValidator.correctTagName(tagName), result);
    }

    @ParameterizedTest
    @MethodSource("correctCertificateDescriptionArgs")
    void correctCertificateDescription(String description, boolean result) {
        assertEquals(GiftEntityValidator.correctCertificateDescription(description), result);
    }

    @ParameterizedTest
    @MethodSource("correctTagNamesArgs")
    void correctTagNames(String tagNames, boolean result) {
        assertEquals(GiftEntityValidator.correctTagNames(tagNames), result);
    }

    @ParameterizedTest
    @MethodSource("correctOptionalParametersArgs")
    void correctOptionalParameters(String name,
                                   String description,
                                   String tagNames,
                                   String sortType,
                                   String direction,
                                   Integer limit,
                                   Integer offset,
                                   boolean result) {
        assertEquals(GiftEntityValidator.correctOptionalParameters(name, description, tagNames, sortType,
                direction, limit, offset), result);
    }

    @ParameterizedTest
    @MethodSource("correctTagArgs")
    void correctTag(TagDTO tag, boolean result) {
        assertEquals(GiftEntityValidator.correctTag(tag), result);
    }

    @ParameterizedTest
    @MethodSource("correctGiftCertificateArgs")
    void correctGiftCertificate(GiftCertificateDTO giftCertificate, boolean result) {
        assertEquals(GiftEntityValidator.correctGiftCertificate(giftCertificate), result);
    }

    @ParameterizedTest
    @MethodSource("correctOptionalCertificateNameArgs")
    void correctOptionalCertificateName(String name, boolean result) {
        assertEquals(GiftEntityValidator.correctOptionalCertificateName(name), result);
    }

    @ParameterizedTest
    @MethodSource("correctGiftCertificateOptionalArgs")
    void correctGiftCertificateOptional(GiftCertificateDTO source, boolean result) {
        assertEquals(GiftEntityValidator.correctGiftCertificateOptional(source), result);
    }

    @ParameterizedTest
    @MethodSource("correctUserRegistrationDataArgs")
    void correctUserRegistrationData(UserRegistrationDTO source, boolean result) {
        assertEquals(GiftEntityValidator.correctUserRegistrationData(source), result);
    }
}
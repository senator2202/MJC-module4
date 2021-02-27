package com.epam.esm.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for PageableProvider methods, providing Pageable objects for jpa repositories
 */
class PageableProviderTest {

    static Stream<Arguments> argsPageableWithSort() {
        return Stream.of(
                Arguments.of(null, null, null, null, PageRequest.of(0, Integer.MAX_VALUE)),
                Arguments.of(10, null, null, null, PageRequest.of(10, Integer.MAX_VALUE)),
                Arguments.of(null, null, "price", null,
                        PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.ASC, "price"))),
                Arguments.of(null, null, null, "asc", PageRequest.of(0, Integer.MAX_VALUE)),
                Arguments.of(null, 10, null, null, PageRequest.of(0, 10)),
                Arguments.of(null, 10, "duration", null,
                        PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "duration"))),
                Arguments.of(null, 10, "createDate", "desc",
                        PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createDate"))),
                Arguments.of(2, 10, "lastUpdateDate", "asc",
                        PageRequest.of(2, 10, Sort.by(Sort.Direction.ASC, "lastUpdateDate"))),
                Arguments.of(2, 10, null, null, PageRequest.of(2, 10))
        );
    }

    static Stream<Arguments> argsPageable() {
        return Stream.of(
                Arguments.of(null, null, Pageable.unpaged()),
                Arguments.of(null, 10, PageRequest.of(0, 10)),
                Arguments.of(2, 10, PageRequest.of(2, 10))
        );
    }

    @ParameterizedTest
    @MethodSource("argsPageableWithSort")
    void pageableWithSort(Integer page, Integer size, String sort, String direction, Pageable result) {
        assertEquals(result, PageableProvider.pageableWithSort(page, size, sort, direction));
    }

    @ParameterizedTest
    @MethodSource("argsPageable")
    void pageable(Integer page, Integer size, Pageable result) {
        assertEquals(result, PageableProvider.pageable(page, size));
    }
}
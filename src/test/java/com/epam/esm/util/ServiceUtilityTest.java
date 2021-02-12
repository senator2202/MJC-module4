package com.epam.esm.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServiceUtilityTest {

    @Test
    void getCurrentDateIso() {
        String regex = "^202\\d-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\d.\\d+$";
        assertTrue(ServiceUtility.getCurrentDateIso().matches(regex));
    }

    @ParameterizedTest
    @MethodSource("argsPageableWithSort")
    void pageableWithSort(Integer page, Integer size, String sort, String direction, Optional<Pageable> result) {
        assertEquals(result, ServiceUtility.pageableWithSort(page, size, sort, direction));
    }

    static Stream<Arguments> argsPageableWithSort() {
        return Stream.of(
                Arguments.of(null, null, null, null, Optional.empty()),
                Arguments.of(10, null, null, null, Optional.empty()),
                Arguments.of(null, null, "price", null, Optional.empty()),
                Arguments.of(null, null, null, "asc", Optional.empty()),
                Arguments.of(null, 10, null, null, Optional.of(PageRequest.of(0,10))),
                Arguments.of(null, 10, "price", null,
                        Optional.of(PageRequest.of(0,10, Sort.by(Sort.Direction.ASC, "price")))),
                Arguments.of(null, 10, "price", "desc",
                        Optional.of(PageRequest.of(0,10, Sort.by(Sort.Direction.DESC, "price")))),
                Arguments.of(null, 10, "price", "asc",
                        Optional.of(PageRequest.of(0,10, Sort.by(Sort.Direction.ASC, "price")))),
                Arguments.of(2, 10, "price", "desc",
                        Optional.of(PageRequest.of(2,10, Sort.by(Sort.Direction.DESC, "price")))),
                Arguments.of(2, 10, null, null, Optional.of(PageRequest.of(2,10)))
        );
    }

    @ParameterizedTest
    @MethodSource("argsPageable")
    void pageable(Integer page, Integer size, Optional<Pageable> result) {
        assertEquals(result, ServiceUtility.pageable(page, size));
    }

    static Stream<Arguments> argsPageable() {
        return Stream.of(
                Arguments.of(null, null, Optional.empty()),
                Arguments.of(null, 10, Optional.of(PageRequest.of(0, 10))),
                Arguments.of(2, 10, Optional.of(PageRequest.of(2, 10)))
        );
    }
}
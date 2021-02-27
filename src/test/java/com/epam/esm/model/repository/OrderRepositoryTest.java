package com.epam.esm.model.repository;

import com.epam.esm.app.SpringBootRestApplication;
import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.entity.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for OrderRepository methods, using in application
 */
@SpringBootTest(classes = SpringBootRestApplication.class)
@Sql(scripts = {"classpath:controller-script.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    static Stream<Arguments> argsFindUserOrders() {
        return Stream.of(
                Arguments.of(Pageable.unpaged(), 8),
                Arguments.of(PageRequest.of(0, 5), 5),
                Arguments.of(PageRequest.of(1, 5), 3),
                Arguments.of(PageRequest.of(10, 20), 0)
        );
    }

    @Test
    void add() {
        Order created = orderRepository.save(StaticDataProvider.ADDING_ORDER);
        List<Order> orders = orderRepository.findOrdersByUserId(1L, Pageable.unpaged());
        assertTrue(created.getId() != null && orders.size() == 9);
    }

    @Test
    void findById() {
        assertTrue(orderRepository.findById(1L).isPresent());
    }

    @ParameterizedTest
    @MethodSource("argsFindUserOrders")
    void findOrdersByUserId(Pageable pageable, int size) {
        assertEquals(size, orderRepository.findOrdersByUserId(1L, pageable).size());
    }

    @Test
    void findMostPopularTagsOfUser() {
        assertEquals(3L, orderRepository.findMostPopularTagsOfUser(3L, PageRequest.of(0, 1)).get(0).getId());
    }

    @Test
    void findUsersWithHighestOrderSum() {
        assertEquals(3L, orderRepository.findUsersWithHighestOrderSum(PageRequest.of(0, 1)).get(0).getId());
    }

    @Test
    void deleteOrdersByGiftCertificateId() {
        orderRepository.deleteOrdersByGiftCertificateId(1L);
        assertEquals(15, orderRepository.findAll().size());
    }
}
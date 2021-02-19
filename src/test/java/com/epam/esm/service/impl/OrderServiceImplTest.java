package com.epam.esm.service.impl;

import com.epam.esm.controller.error_handler.ProjectError;
import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.exception.ExceptionProvider;
import com.epam.esm.exception.GiftEntityNotFoundException;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.repository.GiftCertificateRepository;
import com.epam.esm.model.repository.OrderRepository;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class OrderServiceImplTest {

    @InjectMocks
    private final OrderService service = new OrderServiceImpl();
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private GiftCertificateRepository giftCertificateRepository;
    @Mock
    private ExceptionProvider exceptionProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addExistingUserExistingCertificate() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(StaticDataProvider.USER));
        when(giftCertificateRepository.findById(1L)).thenReturn(Optional.of(StaticDataProvider.GIFT_CERTIFICATE));
        when(orderRepository.save(any(Order.class))).thenReturn(StaticDataProvider.ORDER);
        OrderDTO actual = service.add(1L, 1L);
        OrderDTO expected = StaticDataProvider.ORDER_DTO;
        assertEquals(actual, expected);
    }

    @Test
    void addUserNotExisting() {
        when(exceptionProvider.giftEntityNotFoundException(any(ProjectError.class)))
                .thenReturn(new GiftEntityNotFoundException("", 1));
        when(userRepository.findById(11111L)).thenReturn(Optional.empty());
        assertThrows(GiftEntityNotFoundException.class, () -> service.add(11111L, 1L));
    }

    @Test
    void addCertificateNotExisting() {
        when(exceptionProvider.giftEntityNotFoundException(any(ProjectError.class)))
                .thenReturn(new GiftEntityNotFoundException("", 1));
        when(userRepository.findById(1L)).thenReturn(Optional.of(StaticDataProvider.USER));
        when(giftCertificateRepository.findById(11111L)).thenReturn(Optional.empty());
        assertThrows(GiftEntityNotFoundException.class, () -> service.add(1L, 11111L));
    }

    @Test
    void findByIdExisting() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(StaticDataProvider.USER));
        when(orderRepository.findById(1L)).thenReturn(Optional.of(StaticDataProvider.ORDER));
        Optional<OrderDTO> actual = service.findUserOrderById(1L, 1L);
        Optional<OrderDTO> expected = Optional.of(StaticDataProvider.ORDER_DTO);
        assertEquals(actual, expected);
    }

    @Test
    void findUserOrderByIdNotExisting() {
        when(userRepository.findById(11111L)).thenReturn(Optional.empty());
        when(exceptionProvider.giftEntityNotFoundException(any(ProjectError.class)))
                .thenReturn(new GiftEntityNotFoundException("", 1));
        assertThrows(GiftEntityNotFoundException.class, () -> service.findUserOrderById(11111L, 1L));
    }

    @Test
    void findOrdersByUserId() {
        when(orderRepository.findOrdersByUserId(anyLong(), any(Pageable.class)))
                .thenReturn(Collections.nCopies(5, StaticDataProvider.ORDER));
        List<OrderDTO> actual = service.findOrdersByUserId(1L, 2, 5);
        List<OrderDTO> expected = Collections.nCopies(5, StaticDataProvider.ORDER_DTO);
        assertEquals(actual, expected);
    }

    @Test
    void mostWidelyUsedTagOfUserWithHighestOrdersSum() {
        when(orderRepository.findUsersWithHighestOrderSum(any(Pageable.class)))
                .thenReturn(StaticDataProvider.USER_LIST);
        when(orderRepository.findMostPopularTagsOfUser(anyLong(), any(Pageable.class)))
                .thenReturn(StaticDataProvider.TAG_LIST);
        Optional<TagDTO> actual = service.mostWidelyUsedTagOfUserWithHighestOrdersSum();
        Optional<TagDTO> expected = Optional.of(StaticDataProvider.TAG_DTO);
        assertEquals(actual, expected);
    }
}
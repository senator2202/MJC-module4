package com.epam.esm.service.impl;

import com.epam.esm.controller.error_handler.ProjectError;
import com.epam.esm.exception.ExceptionProvider;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.GiftCertificateRepository;
import com.epam.esm.model.repository.OrderRepository;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.DateTimeUtility;
import com.epam.esm.util.ObjectConverter;
import com.epam.esm.util.PageableProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The type Order service.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Pageable FIRST_PAGE_SINGLE_RESULT_PAGEABLE = PageRequest.of(0, 1);
    private static final int FIRST_ELEMENT_INDEX = 0;

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private GiftCertificateRepository giftCertificateRepository;
    private ExceptionProvider exceptionProvider;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setGiftCertificateRepository(GiftCertificateRepository giftCertificateRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
    }

    @Autowired
    public void setExceptionProvider(ExceptionProvider exceptionProvider) {
        this.exceptionProvider = exceptionProvider;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderDTO add(long userId, long certificateId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> exceptionProvider.giftEntityNotFoundException(ProjectError.USER_NOT_FOUND)
        );
        GiftCertificate giftCertificate = giftCertificateRepository.findById(certificateId).orElseThrow(
                () -> exceptionProvider.giftEntityNotFoundException(ProjectError.GIFT_CERTIFICATE_NOT_FOUND)
        );
        Order orderEntity = new Order();
        orderEntity.setUser(user);
        orderEntity.setGiftCertificate(giftCertificate);
        orderEntity.setCost(giftCertificate.getPrice());
        orderEntity.setOrderDate(DateTimeUtility.getCurrentDateIso());
        return ObjectConverter.toOrderDTO(orderRepository.save(orderEntity));
    }

    @Override
    @Transactional
    public Optional<OrderDTO> findUserOrderById(long userId, long orderId) {
        userRepository.findById(userId).orElseThrow(
                () -> exceptionProvider.giftEntityNotFoundException(ProjectError.USER_NOT_FOUND)
        );
        return orderRepository.findById(orderId)
                .filter(o -> o.getUser().getId() == userId)
                .map(ObjectConverter::toOrderDTO);
    }

    @Override
    public List<OrderDTO> findOrdersByUserId(long userId, Integer page, Integer size) {
        Pageable pageable = PageableProvider.pageable(page, size);
        List<Order> ordersByUserId = orderRepository.findOrdersByUserId(userId, pageable);
        return ObjectConverter.toOrderDTOs(ordersByUserId);
    }

    @Override
    @Transactional
    public Optional<TagDTO> mostWidelyUsedTagOfUserWithHighestOrdersSum() {
        Long userId = orderRepository.findUsersWithHighestOrderSum(FIRST_PAGE_SINGLE_RESULT_PAGEABLE)
                .get(FIRST_ELEMENT_INDEX).getId();
        Tag tag = orderRepository.findMostPopularTagsOfUser(userId, FIRST_PAGE_SINGLE_RESULT_PAGEABLE)
                .get(FIRST_ELEMENT_INDEX);
        return Optional.ofNullable(tag).map(ObjectConverter::toTagDTO);
    }

}

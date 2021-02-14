package com.epam.esm.service.impl;

import com.epam.esm.controller.error_handler.ProjectError;
import com.epam.esm.controller.exception.ExceptionProvider;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.GiftCertificateRepository;
import com.epam.esm.model.repository.OrderRepository;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.ObjectConverter;
import com.epam.esm.util.ServiceUtility;
import org.springframework.beans.factory.annotation.Autowired;
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

    private ExceptionProvider exceptionProvider;

    private UserRepository userRepository;
    private OrderRepository orderRepository;
    private GiftCertificateRepository giftCertificateRepository;

    @Autowired
    public void setExceptionProvider(ExceptionProvider exceptionProvider) {
        this.exceptionProvider = exceptionProvider;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setGiftCertificateRepository(GiftCertificateRepository giftCertificateRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
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
        orderEntity.setOrderDate(ServiceUtility.getCurrentDateIso());
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
    public List<OrderDTO> findOrdersByUserId(long userId, Integer limit, Integer offset) {
        Pageable pageable = ServiceUtility.pageable(limit, offset);
        List<Order> ordersByUserId = orderRepository.findOrdersByUserId(userId, pageable);
        return ObjectConverter.toOrderDTOs(ordersByUserId);
    }

}

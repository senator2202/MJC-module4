package com.epam.esm.service.impl;

import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.OrderRepository;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ObjectConverter;
import com.epam.esm.util.ServiceUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type User service.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Pageable FIRST_PAGE_SINGLE_RESULT_PAGEABLE = PageRequest.of(0, 1);
    private static final int FIRST_ELEMENT_INDEX = 0;
    private UserRepository userRepository;
    private OrderRepository orderRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<UserDTO> findById(long id) {
        return userRepository.findById(id).map(ObjectConverter::toUserDTO);
    }

    @Override
    public List<UserDTO> findAll(Integer page, Integer size) {
        Pageable pageable = ServiceUtility.pageable(page, size);
        return userRepository.findAll(pageable).get()
                .map(ObjectConverter::toUserDTO)
                .collect(Collectors.toList());
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

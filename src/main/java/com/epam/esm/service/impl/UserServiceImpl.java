package com.epam.esm.service.impl;

import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.QOrder;
import com.epam.esm.model.entity.QUser;
import com.epam.esm.model.repository.OrderRepository;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ObjectConverter;
import com.epam.esm.util.ServiceUtility;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import org.springframework.beans.factory.annotation.Autowired;
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

    private UserDao userDao;
    private OrderDao orderDao;
    private TagDao tagDao;

    private UserRepository userRepository;
    private OrderRepository orderRepository;
    private TagRepository tagRepository;

    /**
     * Instantiates a new User service.
     *
     * @param userDao  the user dao
     * @param orderDao the order dao
     * @param tagDao   the tag dao
     */
    @Autowired
    public UserServiceImpl(UserDao userDao, OrderDao orderDao, TagDao tagDao) {
        this.userDao = userDao;
        this.orderDao = orderDao;
        this.tagDao = tagDao;
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
    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
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

    /*
    * "SELECT user_id FROM \n" +
                    "(SELECT user_id, SUM(cost) AS total_cost FROM `order`\n" +
                    "GROUP BY user_id order BY total_cost DESC LIMIT 1) AS tmp";*/

    @Override
    @Transactional
    public Optional<TagDTO> mostWidelyUsedTagOfUserWithHighestOrdersSum() {
        QUser userModel = QUser.user;
        QOrder orderModel = QOrder.order;

        Long userId = userDao.userIdWithHighestOrderSum();
        Long tagId = orderDao.selectMostPopularTagIdOfUser(userId);
        return tagRepository.findById(tagId).map(ObjectConverter::toTagDTO);
    }
}

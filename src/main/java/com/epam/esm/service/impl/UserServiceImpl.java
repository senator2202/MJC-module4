package com.epam.esm.service.impl;

import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ObjectConverter;
import com.epam.esm.util.ServiceUtility;
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

    @Override
    public Optional<UserDTO> findById(long id) {
        return userDao.findById(id).map(ObjectConverter::toUserDTO);
    }

    @Override
    public List<UserDTO> findAll(Integer page, Integer size) {
        Pageable pageable = ServiceUtility.pageable(page, size);
        return userDao.findAll(pageable).get()
                .map(ObjectConverter::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<TagDTO> mostWidelyUsedTagOfUserWithHighestOrdersSum() {
        Long userId = userDao.userIdWithHighestOrderSum();
        Long tagId = orderDao.selectMostPopularTagIdOfUser(userId);
        return tagDao.findById(tagId).map(ObjectConverter::toTagDTO);
    }
}

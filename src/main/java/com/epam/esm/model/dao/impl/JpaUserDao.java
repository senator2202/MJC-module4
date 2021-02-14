package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * The type Jpa user dao.
 */
@Repository
public class JpaUserDao extends AbstractJpaDao<User> implements UserDao {

    private static final String JPQL_FIND_ALL = "select u from User u";
    private static final String SQL_SELECT_USER_ID_WITH_HIGHEST_ORDER_SUM =
            "SELECT user_id FROM \n" +
                    "(SELECT user_id, SUM(cost) AS total_cost FROM `order`\n" +
                    "GROUP BY user_id order BY total_cost DESC LIMIT 1) AS tmp";

    @Autowired
    public void setJpaRepository(UserRepository userRepository) {
        jpaRepository = userRepository;
    }

    @Override
    public Long userIdWithHighestOrderSum() {
        BigInteger bigInteger = (BigInteger) entityManager.
                createNativeQuery(SQL_SELECT_USER_ID_WITH_HIGHEST_ORDER_SUM)
                .getSingleResult();
        return bigInteger.longValue();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable);
    }

    @Override
    public User add(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User update(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }
}

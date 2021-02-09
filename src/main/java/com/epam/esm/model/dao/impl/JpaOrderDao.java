package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.entity.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;

/**
 * The type Jpa order dao.
 */
@Repository
public class JpaOrderDao extends AbstractJpaDao<Order> implements OrderDao {

    private static final String JPQL_FIND_USER_ORDERS = "select o from Order o where o.user.id = ?1";
    private static final String SQL_SELECT_MOST_POPULAR_TAG_OF_USER =
            "SELECT tag_id FROM (SELECT tag_id, COUNT(tag_id) AS most_popular \n" +
                    "FROM `order` JOIN certificate_tag ON `order`.certificate_id=certificate_tag.gift_certificate_id \n" +
                    "WHERE user_id = ?\n" +
                    "GROUP BY tag_id\n" +
                    "order BY most_popular DESC\n" +
                    "LIMIT 1) AS temp";

    @Override
    public List<Order> findOrdersByUserId(long userId, Integer limit, Integer offset) {
        TypedQuery<Order> query = entityManager.createQuery(JPQL_FIND_USER_ORDERS, Order.class);
        if (limit != null) {
            query.setMaxResults(limit);
        }
        if (offset != null) {
            query.setFirstResult(offset);
        }
        return query
                .setParameter(1, userId)
                .getResultList();
    }

    @Override
    public Long selectMostPopularTagIdOfUser(long userId) {
        BigInteger bigInteger = (BigInteger) entityManager
                .createNativeQuery(SQL_SELECT_MOST_POPULAR_TAG_OF_USER)
                .setParameter(1, userId)
                .getSingleResult();
        return bigInteger.longValue();
    }

    @Override
    protected Class<Order> getEntityClass() {
        return Order.class;
    }
}

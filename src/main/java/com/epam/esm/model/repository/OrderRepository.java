package com.epam.esm.model.repository;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The repository for Order entity.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, QuerydslPredicateExecutor<Order> {

    /**
     * Delete orders by gift certificate id.
     *
     * @param id the id
     */
    void deleteOrdersByGiftCertificateId(long id);

    /**
     * Find orders by user id list.
     *
     * @param userId   the user id
     * @param pageable the pageable
     * @return the list
     */
    List<Order> findOrdersByUserId(long userId, Pageable pageable);

    /**
     * Find users with highest order sum list.
     *
     * @param pageable the pageable
     * @return the list
     */
    @Query("select o.user from Order o group by o.user.id order by sum(o.cost) desc")
    List<User> findUsersWithHighestOrderSum(Pageable pageable);

    /**
     * Find most popular tags of user list.
     *
     * @param userId   the user id
     * @param pageable the pageable
     * @return the list
     */
    @Query("select t " +
            "from Order o join o.giftCertificate.tags t " +
            "where o.user.id = :userId " +
            "group by t.id " +
            "order by count (t.id) desc, t.id asc")
    List<Tag> findMostPopularTagsOfUser(@Param("userId") long userId, Pageable pageable);
}

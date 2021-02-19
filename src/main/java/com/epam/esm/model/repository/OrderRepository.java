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

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, QuerydslPredicateExecutor<Order> {

    void deleteOrdersByGiftCertificateId(long id);

    List<Order> findOrdersByUserId(long userId, Pageable pageable);

    @Query("select o.user from Order o group by o.user.id order by sum(o.cost) desc")
    List<User> findUsersWithHighestOrderSum(Pageable pageable);

    @Query("select t " +
            "from Order o join o.giftCertificate.tags t " +
            "where o.user.id = :userId " +
            "group by t.id " +
            "order by count (t.id) desc, t.id asc")
    List<Tag> findMostPopularTagsOfUser(@Param("userId") long userId, Pageable pageable);
}

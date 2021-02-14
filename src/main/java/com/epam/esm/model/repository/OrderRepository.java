package com.epam.esm.model.repository;

import com.epam.esm.model.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, QuerydslPredicateExecutor<Order> {

    void deleteOrderByGiftCertificateId(long id);

    List<Order> findOrdersByUserId(long userId, Pageable pageable);
}

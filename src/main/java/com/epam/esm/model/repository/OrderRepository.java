package com.epam.esm.model.repository;

import com.epam.esm.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    void deleteOrderByGiftCertificateId(long id);
}

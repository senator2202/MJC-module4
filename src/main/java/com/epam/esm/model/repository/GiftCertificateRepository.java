package com.epam.esm.model.repository;

import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>,
        QuerydslPredicateExecutor<GiftCertificate> {
}

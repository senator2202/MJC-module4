package com.epam.esm.model.repository.specification;

import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class GiftCertificateNameLikeSpecification implements Specification<GiftCertificate> {

    private final String name;

    public GiftCertificateNameLikeSpecification(String name) {
        this.name = name;
    }

    @Override
    public Predicate toPredicate(Root<GiftCertificate> root,
                                 CriteriaQuery<?> criteriaQuery,
                                 CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }
}

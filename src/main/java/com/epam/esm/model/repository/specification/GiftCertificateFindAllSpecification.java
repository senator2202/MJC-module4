package com.epam.esm.model.repository.specification;

import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class GiftCertificateFindAllSpecification implements Specification<GiftCertificate> {
    @Override
    public Predicate toPredicate(Root<GiftCertificate> root,
                                 CriteriaQuery<?> criteriaQuery,
                                 CriteriaBuilder criteriaBuilder) {
        return root.isNotNull();
    }
}

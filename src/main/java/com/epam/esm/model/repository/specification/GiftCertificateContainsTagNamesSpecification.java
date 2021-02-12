package com.epam.esm.model.repository.specification;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

public class GiftCertificateContainsTagNamesSpecification implements Specification<GiftCertificate> {

    private final String[] tagNames;

    public GiftCertificateContainsTagNamesSpecification(String[] tagNames) {
        this.tagNames = tagNames;
    }

    @Override
    public Predicate toPredicate(Root<GiftCertificate> root,
                                 CriteriaQuery<?> criteriaQuery,
                                 CriteriaBuilder criteriaBuilder) {
        criteriaBuilder.in(root.get(""));
        return null;
    }
}

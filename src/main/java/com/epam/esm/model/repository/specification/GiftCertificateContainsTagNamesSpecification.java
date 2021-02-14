package com.epam.esm.model.repository.specification;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

public class GiftCertificateContainsTagNamesSpecification implements Specification<GiftCertificate> {

    private final String[] tagNames;

    public GiftCertificateContainsTagNamesSpecification(String[] tagNames) {
        this.tagNames = tagNames;
    }

    @Override
    public Predicate toPredicate(Root<GiftCertificate> root,
                                 CriteriaQuery<?> criteriaQuery,
                                 CriteriaBuilder criteriaBuilder) {
        List<String> list = Arrays.asList(tagNames);
        Join<GiftCertificate, Tag> join = root.join("tags");
        Predicate in = join.get("name").in(list);

        return join.get("name").in(list);

        /*Join<GiftCertificate, Tag> join = root.join("tags");
        Predicate predicate = criteriaBuilder.conjunction();
        for (String tagName : tagNames) {
            Predicate namePredicate = criteriaBuilder.equal(join.get("name"), tagName);
            predicate = criteriaBuilder.and(predicate, namePredicate);
        }
        return predicate;*/
    }
}

package com.epam.esm.util;

import com.epam.esm.model.entity.QGiftCertificate;
import com.epam.esm.validator.GiftEntityValidator;
import com.querydsl.core.types.dsl.BooleanExpression;

public class GiftCertificateExpressionUtility {

    private GiftCertificateExpressionUtility() {
    }

    public static BooleanExpression containsName(String name) {
        return name != null
                ? QGiftCertificate.giftCertificate.name.containsIgnoreCase(name)
                : null;
    }

    public static BooleanExpression containsDescription(String description) {
        return description != null
                ? QGiftCertificate.giftCertificate.description.containsIgnoreCase(description)
                : null;
    }

    public static BooleanExpression hasTags(String tagNames) {
        if (tagNames == null) {
            return null;
        }
        BooleanExpression filterExpression = QGiftCertificate.giftCertificate.isNotNull();
        String[] tagArray = tagNames.split(GiftEntityValidator.TAG_SPLITERATOR);
        for (String s : tagArray) {
            filterExpression = filterExpression.and(QGiftCertificate.giftCertificate.tags.any().name.eq(s));
        }
        return filterExpression;
    }
}

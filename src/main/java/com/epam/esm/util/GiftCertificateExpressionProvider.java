package com.epam.esm.util;

import com.epam.esm.model.entity.QGiftCertificate;
import com.epam.esm.validator.GiftEntityValidator;
import com.querydsl.core.types.dsl.BooleanExpression;

/**
 * Class provides static methods that return BooleanExpression objects for GiftCertificate Entities
 */
public class GiftCertificateExpressionProvider {

    private GiftCertificateExpressionProvider() {
    }

    /**
     * Method returns BooleanExpression (QueryDsl predicate), according to input parameters
     *
     * @param name        the name
     * @param description the description
     * @param tagNames    the tag names
     * @return the boolean expression
     */
    public static BooleanExpression getBooleanExpression(String name, String description, String tagNames) {
        QGiftCertificate certificateModel = QGiftCertificate.giftCertificate;
        BooleanExpression filterExpression = certificateModel.isNotNull();
        if (name != null) {
            filterExpression = filterExpression.and(certificateModel.name.containsIgnoreCase(name));
        }
        if (description != null) {
            filterExpression = filterExpression.and(certificateModel.description.containsIgnoreCase(description));
        }
        if (tagNames != null) {
            String[] tagArray = tagNames.split(GiftEntityValidator.TAG_SPLITERATOR);
            for (String s : tagArray) {
                filterExpression = filterExpression.and(certificateModel.tags.any().name.eq(s));
            }
        }
        return filterExpression;
    }
}

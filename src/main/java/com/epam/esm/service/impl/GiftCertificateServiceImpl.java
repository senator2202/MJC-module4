package com.epam.esm.service.impl;

import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.QGiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.GiftCertificateRepository;
import com.epam.esm.model.repository.OrderRepository;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.ObjectConverter;
import com.epam.esm.util.ServiceUtility;
import com.epam.esm.validator.GiftEntityValidator;
import com.google.common.base.CaseFormat;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Gift certificate service.
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final String DASH = "-";
    private static final String UNDER_SCOPE = "_";

    private GiftCertificateRepository giftCertificateRepository;
    private OrderRepository orderRepository;
    private TagRepository tagRepository;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository,
                                      OrderRepository orderRepository,
                                      TagRepository tagRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.orderRepository = orderRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public Optional<GiftCertificateDTO> findById(long id) {
        return giftCertificateRepository.findById(id).map(ObjectConverter::toGiftCertificateDTO);
    }

    @Override
    public List<GiftCertificateDTO> findAll(String name, String description, String tagNames,
                                            String sortType, String direction, Integer page, Integer size) {
        BooleanExpression filterExpression = getBooleanExpression(name, description, tagNames);
        Pageable pageable = ServiceUtility.pageableWithSort(page, size, toCamelCase(sortType), direction);
        return giftCertificateRepository.findAll(filterExpression, pageable).get()
                .map(ObjectConverter::toGiftCertificateDTO)
                .collect(Collectors.toList());
    }

    /**
     * Method returns BooleanExpression (QueryDsl predicate), according to input parameters
     *
     * @param name        the name
     * @param description the description
     * @param tagNames    the tag names
     * @return the boolean expression
     */
    private BooleanExpression getBooleanExpression(String name, String description, String tagNames) {
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

    /**
     * Method returns source string in camel case format
     *
     * @param source        the name
     * @return the modified source String
     */
    private String toCamelCase(String source) {
        if (source != null) {
            source = source.replace(DASH, UNDER_SCOPE);
            source = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, source);
        }
        return source;
    }

    @Override
    @Transactional
    public GiftCertificateDTO add(GiftCertificateDTO certificate) {
        String currentDate = ServiceUtility.getCurrentDateIso();
        certificate.setCreateDate(currentDate);
        certificate.setLastUpdateDate(currentDate);
        GiftCertificate entity = ObjectConverter.toGiftCertificateEntity(certificate);
        findTagsInDB(entity);
        return ObjectConverter.toGiftCertificateDTO(giftCertificateRepository.save(entity));
    }

    @Override
    @Transactional
    public Optional<GiftCertificateDTO> update(GiftCertificateDTO certificate) {
        Optional<GiftCertificate> optional = giftCertificateRepository.findById(certificate.getId());
        if (optional.isPresent()) {
            GiftCertificate found = optional.get();
            updateNotEmptyFields(certificate, found);
            found.setLastUpdateDate(ServiceUtility.getCurrentDateIso());
            GiftCertificate updated = giftCertificateRepository.save(found);
            optional = Optional.of(updated);
        }
        return optional.map(ObjectConverter::toGiftCertificateDTO);
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        if (giftCertificateRepository.existsById(id)) {
            orderRepository.deleteOrderByGiftCertificateId(id);
            giftCertificateRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method copies non-empty fields from DTO object to entity object, found in DB
     */
    private void updateNotEmptyFields(GiftCertificateDTO source, GiftCertificate found) {
        if (source.getName() != null) {
            found.setName(source.getName());
        }
        if (source.getDescription() != null) {
            found.setDescription(source.getDescription());
        }
        if (source.getPrice() != null) {
            found.setPrice(source.getPrice());
        }
        if (source.getDuration() != null) {
            found.setDuration(source.getDuration());
        }
        if (source.getTags() != null) {
            GiftCertificate entity = ObjectConverter.toGiftCertificateEntity(source);
            findTagsInDB(entity);
            found.setTags(entity.getTags());
        }
    }

    /**
     * Method updates GiftCertificate tags. It iterates on list of tags, looks for tag with the same name in db.
     * If tag with the same name found, replace tag in list on existing tag from DB.
     */
    private void findTagsInDB(GiftCertificate source) {
        List<Tag> tags = source.getTags();
        if (tags != null) {
            ListIterator<Tag> iterator = tags.listIterator();
            while (iterator.hasNext()) {
                Tag tag = iterator.next();
                Optional<Tag> optionalTag = tagRepository.findByName(tag.getName());
                optionalTag.ifPresent(iterator::set);
            }
        }
    }
}

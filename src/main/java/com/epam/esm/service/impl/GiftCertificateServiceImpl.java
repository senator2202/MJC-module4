package com.epam.esm.service.impl;

import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.GiftCertificateRepository;
import com.epam.esm.model.repository.OrderRepository;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.DateTimeUtility;
import com.epam.esm.util.GiftCertificateExpressionProvider;
import com.epam.esm.util.ObjectConverter;
import com.epam.esm.util.PageableProvider;
import com.google.common.base.CaseFormat;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

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
    public void setGiftCertificateRepository(GiftCertificateRepository giftCertificateRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Optional<GiftCertificateDTO> findById(long id) {
        return giftCertificateRepository.findById(id).map(ObjectConverter::toGiftCertificateDTO);
    }

    @Override
    public List<GiftCertificateDTO> findAll(String name, String description, String tagNames,
                                            String sortType, String direction, Integer page, Integer size) {
        BooleanExpression filterExpression =
                GiftCertificateExpressionProvider.getBooleanExpression(name, description, tagNames);
        Pageable pageable = PageableProvider.pageableWithSort(page, size, toCamelCase(sortType), direction);
        Page<GiftCertificate> giftCertificatePage = giftCertificateRepository.findAll(filterExpression, pageable);
        return ObjectConverter.toGiftCertificateDTOs(giftCertificatePage.getContent());
    }

    /**
     * Method returns source string in camel case format
     *
     * @param source the source String object
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
        String currentDate = DateTimeUtility.getCurrentDateIso();
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
            found.setLastUpdateDate(DateTimeUtility.getCurrentDateIso());
            GiftCertificate updated = giftCertificateRepository.save(found);
            optional = Optional.of(updated);
        }
        return optional.map(ObjectConverter::toGiftCertificateDTO);
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        if (giftCertificateRepository.existsById(id)) {
            orderRepository.deleteOrdersByGiftCertificateId(id);
            giftCertificateRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method copies non-empty fields from DTO object to entity object, found in DB
     *
     * @param source the source dto object
     * @param found  the found entity object
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
     *
     * @param source the source object, which tags we will look for
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

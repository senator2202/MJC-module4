package com.epam.esm.service.impl;

import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.GiftCertificateRepository;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ObjectConverter;
import com.epam.esm.util.PageableProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The type Tag service.
 */
@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;
    private GiftCertificateRepository giftCertificateRepository;

    @Autowired
    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Autowired
    public void setGiftCertificateRepository(GiftCertificateRepository giftCertificateRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
    }

    @Override
    public Optional<TagDTO> findById(long id) {
        return tagRepository.findById(id).map(ObjectConverter::toTagDTO);
    }

    @Override
    public List<TagDTO> findAll(Integer page, Integer size) {
        Pageable pageable = PageableProvider.pageable(page, size);
        Page<Tag> tagPage = tagRepository.findAll(pageable);
        return ObjectConverter.toTagDTOs(tagPage.getContent());
    }

    @Override
    @Transactional
    public TagDTO add(TagDTO entity) {
        return tagRepository.findByName(entity.getName()).map(ObjectConverter::toTagDTO)
                .orElseGet(() -> ObjectConverter.toTagDTO(tagRepository.save(ObjectConverter.toTagEntity(entity))));
    }

    @Override
    @Transactional
    public Optional<TagDTO> update(TagDTO entity) {
        Optional<Tag> optional = tagRepository.findById(entity.getId());
        return optional.map(t -> ObjectConverter.toTagDTO(tagRepository.save(ObjectConverter.toTagEntity(entity))));
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        Optional<Tag> optional = tagRepository.findById(id);
        if (optional.isPresent()) {
            Tag tag = optional.get();
            List<GiftCertificate> certificates = giftCertificateRepository.findGiftCertificateByTagsContaining(tag);
            certificates.forEach(c -> c.getTags().remove(tag));
            tagRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}

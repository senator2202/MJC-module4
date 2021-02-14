package com.epam.esm.service.impl;

import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ObjectConverter;
import com.epam.esm.util.ServiceUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Tag service.
 */
@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    /**
     * Sets tag repository.
     *
     * @param tagRepository the tag repository
     */
    @Autowired
    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Optional<TagDTO> findById(long id) {
        return tagRepository.findById(id).map(ObjectConverter::toTagDTO);
    }

    @Override
    public List<TagDTO> findAll(Integer page, Integer size) {
        Pageable pageable = ServiceUtility.pageable(page, size);
        return tagRepository.findAll(pageable).get()
                .map(ObjectConverter::toTagDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TagDTO add(TagDTO entity) {
        return tagRepository.findByName(entity.getName()).map(ObjectConverter::toTagDTO)
                .orElseGet(() -> ObjectConverter.toTagDTO(tagRepository.save(ObjectConverter.toTagEntity(entity))));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<TagDTO> update(TagDTO entity) {
        Optional<Tag> optional = tagRepository.findById(entity.getId());
        return optional.map(t -> ObjectConverter.toTagDTO(tagRepository.save(ObjectConverter.toTagEntity(entity))));
    }

    @Override
    public boolean delete(long id) {
        if (tagRepository.existsById(id)) {
            tagRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}

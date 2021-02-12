package com.epam.esm.service.impl;

import com.epam.esm.model.dao.TagDao;
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

    private TagDao tagDao;

    private TagRepository tagRepository;

    /**
     * Instantiates a new Tag service.
     *
     * @param tagDao the tag dao
     */
    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

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
        Optional<Pageable> optional = ServiceUtility.pageable(page, size);
        List<Tag> tags =
                optional.map(pageable -> tagRepository.findAll(pageable).get().collect(Collectors.toList()))
                        .orElseGet(() -> tagRepository.findAll());
        return ObjectConverter.toTagDTOs(tags);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TagDTO add(TagDTO entity) {
        Tag tag = tagRepository.findByName(entity.getName());
        if (tag == null) {
            tag = tagRepository.save(ObjectConverter.toTagEntity(entity));
        }
        return ObjectConverter.toTagDTO(tag);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<TagDTO> update(TagDTO entity) {
        Optional<Tag> optional = tagDao.findById(entity.getId());
        return optional.map(t -> ObjectConverter.toTagDTO(tagDao.update(ObjectConverter.toTagEntity(entity))));
    }

    @Override
    public boolean delete(long id) {
        return tagDao.delete(id);
    }
}

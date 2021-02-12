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

    /**
     * Instantiates a new TagDao.
     *
     * @param tagDao the tag dao
     */
    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Optional<TagDTO> findById(long id) {
        return tagDao.findById(id).map(ObjectConverter::toTagDTO);
    }

    @Override
    public List<TagDTO> findAll(Integer page, Integer size) {
        Pageable pageable = ServiceUtility.pageable(page, size);
        return tagDao.findAll(pageable).get()
                .map(ObjectConverter::toTagDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TagDTO add(TagDTO entity) {
        return tagDao.findByName(entity.getName()).map(ObjectConverter::toTagDTO)
                .orElseGet(() -> ObjectConverter.toTagDTO(tagDao.add(ObjectConverter.toTagEntity(entity))));
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

package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The type Jpa tag dao.
 */
@Repository
public class JpaTagDao extends AbstractJpaDao<Tag> implements TagDao {

    @Autowired
    public void setJpaRepository(TagRepository tagRepository) {
        jpaRepository = tagRepository;
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return ((TagRepository) jpaRepository).findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Tag> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable);
    }

    @Override
    protected Class<Tag> getEntityClass() {
        return Tag.class;
    }
}

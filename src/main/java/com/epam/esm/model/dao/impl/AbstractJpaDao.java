package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.BaseDao;
import com.epam.esm.model.entity.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

/**
 * The type Abstract jpa dao, with some basic method realizations
 *
 * @param <T> the type parameter
 */
@Repository
public abstract class AbstractJpaDao<T extends Entity> implements BaseDao<T> {

    /**
     * The Entity manager.
     */
    @PersistenceContext
    protected EntityManager entityManager;

    protected JpaRepository<T, Long> jpaRepository;

    @Override
    public Optional<T> findById(long id) {
        return jpaRepository.findById(id);
    }

    @Override
    @Transactional
    public T add(T entity) {
        return jpaRepository.save(entity);
    }

    @Override
    @Transactional
    public T update(T entity) {
        return jpaRepository.save(entity);
    }

    @Override
    public boolean delete(long id) {
        if (jpaRepository.existsById(id)) {
            jpaRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets class object of parameterized type. Used in findById method
     *
     * @return the entity class
     */
    protected abstract Class<T> getEntityClass();
}

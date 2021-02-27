package com.epam.esm.model.repository;

import com.epam.esm.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The repository for User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    /**
     * Find by username optional.
     *
     * @param userName the user name
     * @return the optional
     */
    Optional<User> findByUsername(String userName);

    /**
     * Exists by username boolean.
     *
     * @param name the name
     * @return the boolean
     */
    boolean existsByUsername(String name);
}

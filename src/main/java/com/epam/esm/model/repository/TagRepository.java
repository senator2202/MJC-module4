package com.epam.esm.model.repository;

import com.epam.esm.model.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    //Page<Tag> findAll(Pageable pageable);

    @Query("select t from Tag t where t.name = :tagName")
    Tag findByName(@Param("tagName") String tagName);
}

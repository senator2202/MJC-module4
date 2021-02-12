package com.epam.esm.model.repository;

import com.epam.esm.app.SpringBootRestApplication;
import com.epam.esm.model.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpringBootRestApplication.class)
class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    void findAll() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").descending());
        List<Tag> tags = tagRepository.findAll(pageable).get().collect(Collectors.toList());
        int a = 1;
    }
}
package com.epam.esm.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Class provides static method to create Pageable objects.
 */
public class PageableProvider {

    private PageableProvider() {
    }

    /**
     * Method returns Pageable object, according to page, size, sort, direction parameters
     *
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the Pageable object
     */
    public static Pageable pageableWithSort(Integer page, Integer size, String sort, String direction) {
        Pageable pageable;
        int intPage = page != null ? page : 0;
        int intSize = size != null ? size : Integer.MAX_VALUE;
        if (sort != null) {
            Sort.Direction enumDirection = direction != null
                    ? Sort.Direction.valueOf(direction.toUpperCase())
                    : Sort.Direction.ASC;
            pageable = PageRequest.of(intPage, intSize, Sort.by(enumDirection, sort));
        } else {
            pageable = PageRequest.of(intPage, intSize);
        }
        return pageable;
    }

    /**
     * Method returns Pageable object, according to page, size parameters
     *
     * @param page the page
     * @param size the size
     * @return the Pageable object
     */
    public static Pageable pageable(Integer page, Integer size) {
        Pageable pageable;
        int intPage = page != null ? page : 0;
        if (size != null) {
            pageable = PageRequest.of(intPage, size);
        } else {
            pageable = Pageable.unpaged();
        }
        return pageable;
    }
}

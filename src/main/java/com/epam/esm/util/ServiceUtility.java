package com.epam.esm.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Utility class, consists of methods, working with date and time.
 */
public class ServiceUtility {

    private ServiceUtility() {
    }

    /**
     * Gets current date iso.
     *
     * @return the current date iso
     */
    public static String getCurrentDateIso() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * Method returns Pageable object, according to page, size, sort, direction parameters
     *
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the optional
     */
    public static Optional<Pageable> pageableWithSort(Integer page, Integer size, String sort, String direction) {
        Pageable pageable;
        int intPage = page != null ? page : 0;
        if (size != null) {
            if (sort != null) {
                Sort.Direction enumDirection = direction != null
                        ? Sort.Direction.valueOf(direction.toUpperCase())
                        : Sort.Direction.ASC;
                pageable = PageRequest.of(intPage, size, Sort.by(enumDirection, sort));
            } else {
                pageable = PageRequest.of(intPage, size);
            }
        } else {
            pageable = null;
        }
        return Optional.ofNullable(pageable);
    }

    /**
     * Method returns Pageable object, according to page, size parameters
     *
     * @param page the page
     * @param size the size
     * @return the optional
     */
    public static Optional<Pageable> pageable(Integer page, Integer size) {
        Pageable pageable;
        int intPage = page != null ? page : 0;
        if (size != null) {
            pageable = PageRequest.of(intPage, size);
        } else {
            pageable = null;
        }
        return Optional.ofNullable(pageable);
    }
}

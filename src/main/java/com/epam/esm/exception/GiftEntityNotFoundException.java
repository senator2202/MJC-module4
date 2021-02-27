package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

/**
 * The type Gift entity not found exception. Describes situations when user tries to find not existing entities.
 */
public class GiftEntityNotFoundException extends AbstractRuntimeException {

    /**
     * Instantiates a new Gift entity not found exception.
     *
     * @param message   the message
     * @param errorCode the error code
     */
    public GiftEntityNotFoundException(String message, int errorCode) {
        super(message, errorCode, HttpStatus.NOT_FOUND);
    }
}

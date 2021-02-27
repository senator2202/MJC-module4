package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

/**
 * The type User name already exists exception. Describes situation when user tries to register,
 * using existing username.
 */
public class UserNameAlreadyExistsException extends AbstractRuntimeException {

    /**
     * Instantiates a new User name already exists exception.
     *
     * @param message   the message
     * @param errorCode the error code
     */
    public UserNameAlreadyExistsException(String message, int errorCode) {
        super(message, errorCode, HttpStatus.CONFLICT);
    }
}

package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

/**
 * The type Wrong parameter format exception. Describes situation when user enters invalid information.
 */
public class WrongParameterFormatException extends AbstractRuntimeException {

    /**
     * Instantiates a new Wrong parameter format exception.
     *
     * @param message   the message
     * @param errorCode the error code
     */
    public WrongParameterFormatException(String message, int errorCode) {
        super(message, errorCode, HttpStatus.BAD_REQUEST);
    }
}

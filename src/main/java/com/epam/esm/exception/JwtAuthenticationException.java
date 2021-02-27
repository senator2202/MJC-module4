package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

/**
 * The type Jwt authentication exception. Describes situations, when JWT is not valid.
 */
public class JwtAuthenticationException extends AbstractRuntimeException {

    /**
     * Instantiates a new Jwt authentication exception.
     *
     * @param message   the message
     * @param errorCode the error code
     */
    public JwtAuthenticationException(String message, int errorCode) {
        super(message, errorCode, HttpStatus.UNAUTHORIZED);
    }
}

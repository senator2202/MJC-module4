package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

public class JwtAuthenticationException extends AbstractRuntimeException {

    public JwtAuthenticationException(String message, int errorCode) {
        super(message, errorCode, HttpStatus.UNAUTHORIZED);
    }
}

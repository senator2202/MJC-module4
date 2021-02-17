package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

public class GiftEntityNotFoundException extends AbstractRuntimeException {

    public GiftEntityNotFoundException(String message, int errorCode) {
        super(message, errorCode, HttpStatus.NOT_FOUND);
    }
}

package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

public class UserNameAlreadyExistsException extends AbstractRuntimeException {

    public UserNameAlreadyExistsException(String message, int errorCode) {
        super(message, errorCode, HttpStatus.CONFLICT);
    }
}

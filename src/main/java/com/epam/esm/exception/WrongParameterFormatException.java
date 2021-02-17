package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

public class WrongParameterFormatException extends AbstractRuntimeException {

    public WrongParameterFormatException(String message, int errorCode) {
        super(message, errorCode, HttpStatus.BAD_REQUEST);
    }
}

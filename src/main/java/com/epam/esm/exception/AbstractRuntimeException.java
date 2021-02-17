package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

public abstract class AbstractRuntimeException extends RuntimeException {
    private final int errorCode;
    private final HttpStatus httpStatus;

    protected AbstractRuntimeException(String message, int errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

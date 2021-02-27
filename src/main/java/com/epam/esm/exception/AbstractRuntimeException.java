package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

/**
 * The type Abstract runtime exception. Contains
 */
public abstract class AbstractRuntimeException extends RuntimeException {
    /**
     * The Error code.
     */
    private final int errorCode;
    /**
     * The Http status.
     */
    private final HttpStatus httpStatus;

    /**
     * Instantiates a new Abstract runtime exception.
     *
     * @param message    the message
     * @param errorCode  the error code
     * @param httpStatus the http status
     */
    protected AbstractRuntimeException(String message, int errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    /**
     * Gets error code.
     *
     * @return the error code
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Gets http status.
     *
     * @return the http status
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

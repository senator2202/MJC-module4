package com.epam.esm.controller.error_handler;

import com.epam.esm.exception.AbstractRuntimeException;
import com.epam.esm.exception.GiftEntityNotFoundException;
import com.epam.esm.exception.JwtAuthenticationException;
import com.epam.esm.exception.UserNameAlreadyExistsException;
import com.epam.esm.exception.WrongParameterFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Class is responsible for Exception handling.
 */
@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Source for localized messages
     */
    private final ResourceBundleMessageSource messageSource;

    @Autowired
    public CustomRestExceptionHandler(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Method handles custom runtime exceptions.
     *
     * @param e the exception object
     * @return the response entity
     */
    @ExceptionHandler({
            GiftEntityNotFoundException.class,
            WrongParameterFormatException.class,
            UserNameAlreadyExistsException.class,
            JwtAuthenticationException.class
    })
    public ResponseEntity<ApiError> notFound(AbstractRuntimeException e) {
        ApiError apiError = new ApiError(e.getMessage(), e.getErrorCode());
        return new ResponseEntity<>(apiError, e.getHttpStatus());
    }
}

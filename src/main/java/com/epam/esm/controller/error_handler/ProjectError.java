package com.epam.esm.controller.error_handler;

/**
 * The enum with possible errors/exceptional situations in application.
 */
public enum ProjectError {
    /**
     * Gift certificate not found project error.
     */
    GIFT_CERTIFICATE_NOT_FOUND(40401, "certificateNotFound"),
    /**
     * Tag not found project error.
     */
    TAG_NOT_FOUND(40402, "tagNotFound"),
    /**
     * User not found project error.
     */
    USER_NOT_FOUND(40436, "userNotFound"),
    /**
     * Buy parameters wrong format project error.
     */
    BUY_PARAMETERS_WRONG_FORMAT(40037, "wrongBuy"),
    /**
     * Order not found project error.
     */
    ORDER_NOT_FOUND(40448, "orderNotFound"),
    /**
     * Wrong optional parameters project error.
     */
    WRONG_OPTIONAL_PARAMETERS(40001, "wrongOptional"),
    /**
     * Tag wrong parameters project error.
     */
    TAG_WRONG_PARAMETERS(40002, "wrongTag"),
    /**
     * Certificate wrong parameters project error.
     */
    CERTIFICATE_WRONG_PARAMETERS(40003, "wrongCertificate"),
    /**
     * Wrong user registration data project error.
     */
    WRONG_USER_REGISTRATION_DATA(40004, "wrongUserRegistrationData"),
    /**
     * User name already exists project error.
     */
    USER_NAME_ALREADY_EXISTS(40901, "userNameAlreadyExists"),
    /**
     * Invalid username password project error.
     */
    INVALID_USERNAME_PASSWORD(40101, "invalidUsernamePassword");

    /**
     * The Error code of exceptional situation.
     */
    private final int errorCode;
    /**
     * The Message key. Is used in localizing error messages.
     */
    private final String messageKey;

    /**
     * Instantiates a new Project error.
     *
     * @param errorCode  the error code
     * @param messageKey the message key
     */
    ProjectError(int errorCode, String messageKey) {
        this.errorCode = errorCode;
        this.messageKey = messageKey;
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
     * Gets message key.
     *
     * @return the message key
     */
    public String getMessageKey() {
        return messageKey;
    }
}

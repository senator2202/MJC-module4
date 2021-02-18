package com.epam.esm.controller.error_handler;

public enum ProjectError {
    GIFT_CERTIFICATE_NOT_FOUND(40401, "certificateNotFound"),
    TAG_NOT_FOUND(40402, "tagNotFound"),
    USER_NOT_FOUND(40436, "userNotFound"),
    BUY_PARAMETERS_WRONG_FORMAT(40437, "wrongBuy"),
    ORDER_NOT_FOUND(40448, "orderNotFound"),
    WRONG_OPTIONAL_PARAMETERS(40001, "wrongOptional"),
    TAG_WRONG_PARAMETERS(40002, "wrongTag"),
    CERTIFICATE_WRONG_PARAMETERS(40003, "wrongCertificate"),
    WRONG_USER_REGISTRATION_DATA(40004, "wrongUserRegistrationData"),
    USER_NAME_ALREADY_EXISTS(40901, "userNameAlreadyExists"),
    INVALID_USERNAME_PASSWORD(40101, "invalidUsernamePassword");

    private final int errorCode;
    private final String messageKey;

    ProjectError(int errorCode, String messageKey) {
        this.errorCode = errorCode;
        this.messageKey = messageKey;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessageKey() {
        return messageKey;
    }
}

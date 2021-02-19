package com.epam.esm.controller.security;

/**
 * Class, storing information, that is supposed to be written into Authentication.details field.
 */
public class AuthenticationDetails {

    /**
     * The User id.
     */
    private final Long userId;
    /**
     * The Is admin.
     */
    private final boolean isAdmin;

    /**
     * Instantiates a new Authentication details.
     *
     * @param userId  the user id
     * @param isAdmin the is admin
     */
    public AuthenticationDetails(Long userId, boolean isAdmin) {
        this.userId = userId;
        this.isAdmin = isAdmin;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Is admin boolean.
     *
     * @return the boolean
     */
    public boolean isAdmin() {
        return isAdmin;
    }
}

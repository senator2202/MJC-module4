package com.epam.esm.controller.security;

public class AuthenticationDetails {

    private final Long userId;
    private final boolean isAdmin;

    public AuthenticationDetails(Long userId, boolean isAdmin) {
        this.userId = userId;
        this.isAdmin = isAdmin;
    }

    public Long getUserId() {
        return userId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}

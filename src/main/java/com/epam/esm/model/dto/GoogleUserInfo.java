package com.epam.esm.model.dto;

import java.util.Map;
import java.util.Objects;

/**
 * The DTO object for open id connected google user.
 */
public class GoogleUserInfo {

    private static final String GOOGLE_USER_ID = "sub";
    private static final String GOOGLE_USER_NAME = "name";
    private static final String GOOGLE_USER_EMAIL = "email";

    /**
     * The map with google user attributes.
     */
    private final Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getId() {
        return (String) attributes.get(GOOGLE_USER_ID);
    }

    public String getName() {
        return (String) attributes.get(GOOGLE_USER_NAME);
    }

    public String getEmail() {
        return (String) attributes.get(GOOGLE_USER_EMAIL);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GoogleUserInfo that = (GoogleUserInfo) o;

        return Objects.equals(attributes, that.attributes);
    }

    @Override
    public int hashCode() {
        return attributes != null ? attributes.hashCode() : 0;
    }
}
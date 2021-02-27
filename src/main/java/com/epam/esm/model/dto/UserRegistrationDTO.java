package com.epam.esm.model.dto;

/**
 * The DTO object for user registration data
 */
public class UserRegistrationDTO extends UserAuthenticationDTO {

    private String name;
    private String passwordRepeat;

    public UserRegistrationDTO(String name, String username, String password, String passwordRepeat) {
        super(username, password);
        this.name = name;
        this.passwordRepeat = passwordRepeat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }
}

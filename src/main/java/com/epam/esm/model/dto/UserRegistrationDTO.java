package com.epam.esm.model.dto;

/**
 * The DTO object for user registration data
 */
public class UserRegistrationDTO {

    private String name;
    private String userName;
    private String password;
    private String passwordRepeat;

    public UserRegistrationDTO(String name, String userName, String password, String passwordRepeat) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.passwordRepeat = passwordRepeat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }
}

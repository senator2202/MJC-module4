package com.epam.esm.model.dto;

/**
 * The DTO object for authentication request
 */
public class AuthenticationRequestDTO {

    private String userName;
    private String password;

    public AuthenticationRequestDTO() {
    }

    public AuthenticationRequestDTO(String userName, String password) {
        this.userName = userName;
        this.password = password;
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
}

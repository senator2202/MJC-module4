package com.epam.esm.controller.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * The type Security user. Implementation of UserDetails that additionally holds
 * userId and isAdmin variables. They are written into Authentication.details field and are used in
 * PersonalDataFilter.
 */
public class SecurityUser implements UserDetails {

    private final String userName;
    private final String password;
    private final Set<? extends GrantedAuthority> grantedAuthorities;
    private final Long userId;
    private final boolean isAdmin;

    /**
     * Instantiates a new Security user.
     *
     * @param userName           the user name
     * @param password           the password
     * @param grantedAuthorities the granted authorities
     * @param userId             the user id
     * @param isAdmin            the is admin
     */
    public SecurityUser(String userName, String password,
                        Set<? extends GrantedAuthority> grantedAuthorities,
                        Long userId, boolean isAdmin) {
        this.userName = userName;
        this.password = password;
        this.grantedAuthorities = grantedAuthorities;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SecurityUser that = (SecurityUser) o;

        if (isAdmin != that.isAdmin) {
            return false;
        }
        if (!Objects.equals(userName, that.userName)) {
            return false;
        }
        if (!Objects.equals(password, that.password)) {
            return false;
        }
        if (!Objects.equals(grantedAuthorities, that.grantedAuthorities)) {
            return false;
        }
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (grantedAuthorities != null ? grantedAuthorities.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (isAdmin ? 1 : 0);
        return result;
    }
}

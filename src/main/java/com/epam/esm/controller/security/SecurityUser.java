package com.epam.esm.controller.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Objects;
import java.util.Set;

/**
 * The type Security user. Implementation of UserDetails that additionally holds
 * userId and isAdmin variables. They are written into Authentication.details field and are used in
 * PersonalDataFilter.
 */
public class SecurityUser extends User {

    private final AuthenticationDetails authenticationDetails;

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
        super(userName, password, grantedAuthorities);
        authenticationDetails = new AuthenticationDetails(userId, isAdmin);
    }

    public AuthenticationDetails getAuthenticationDetails() {
        return authenticationDetails;
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


        if (!Objects.equals(super.getUsername(), that.getUsername())) {
            return false;
        }
        if (!Objects.equals(getPassword(), that.getPassword())) {
            return false;
        }
        if (!Objects.equals(getAuthorities(), that.getAuthorities())) {
            return false;
        }
        return Objects.equals(authenticationDetails, that.authenticationDetails);
    }

    @Override
    public int hashCode() {
        int result = getUsername() != null ? getUsername().hashCode() : 0;
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getAuthorities() != null ? getAuthorities().hashCode() : 0);
        result = 31 * result + (authenticationDetails != null ? authenticationDetails.hashCode() : 0);
        return result;
    }
}

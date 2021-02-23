package com.epam.esm.service.impl;

import com.epam.esm.model.dto.GoogleUserInfo;
import com.epam.esm.model.entity.Role;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.RoleRepository;
import com.epam.esm.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * The type Custom service for open id connected service.
 * Service additionally saves connected user to database, if necessary.
 */
@Service
public class CustomOidcUserService extends OidcUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomOidcUserService(UserRepository userRepository,
                                 RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        try {
            GoogleUserInfo googleUserDTO = new GoogleUserInfo(oidcUser.getAttributes());
            Optional<User> userOptional = userRepository.findByUsername(googleUserDTO.getEmail());
            if (userOptional.isEmpty()) {
                User user = new User();
                user.setUsername(googleUserDTO.getEmail());
                user.setName(googleUserDTO.getName());
                Role userRole = roleRepository.findUserRole();
                user.setRole(userRole);
                String password = UUID.randomUUID().toString();
                user.setPassword(passwordEncoder.encode(password));
                userRepository.save(user);
            }
            return oidcUser;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }
}

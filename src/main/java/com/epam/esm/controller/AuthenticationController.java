package com.epam.esm.controller;

import com.epam.esm.controller.error_handler.ProjectError;
import com.epam.esm.controller.security.JwtTokenProvider;
import com.epam.esm.exception.ExceptionProvider;
import com.epam.esm.model.dto.UserAuthenticationDTO;
import com.epam.esm.model.dto.UserRegistrationDTO;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.GiftEntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Authentication controller.
 */
@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    private static final String USER_NAME_MAP_KEY = "userName";
    private static final String TOKEN_MAP_KEY = "token";

    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;
    private ExceptionProvider exceptionProvider;
    private UserService userService;

    @Autowired
    public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setExceptionProvider(ExceptionProvider exceptionProvider) {
        this.exceptionProvider = exceptionProvider;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Authentication endpoint.
     *
     * @param request the request object, containing auth data
     * @return the response entity
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody UserAuthenticationDTO request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                    request.getPassword()));
            return getTokenResponseEntity(request.getUsername());
        } catch (AuthenticationException e) {
            throw exceptionProvider.jwtAuthenticationException(ProjectError.INVALID_USERNAME_PASSWORD);
        }
    }

    /**
     * Endpoint for registration new users.
     *
     * @param data the user registration data
     * @return the user dto
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody UserRegistrationDTO data) {
        if (!GiftEntityValidator.correctUserRegistrationData(data)) {
            throw exceptionProvider.wrongParameterFormatException(ProjectError.WRONG_USER_REGISTRATION_DATA);
        }
        userService.add(data);
        return getTokenResponseEntity(data.getUsername());
    }

    /**
     * Method takes username, converts it to token,
     * puts username and token into map and returns its response entity
     *
     * @param userName the user name
     * @return the response entity of map
     */
    private ResponseEntity<Map<String, String>> getTokenResponseEntity(String userName) {
        String token = jwtTokenProvider.createToken(userName);
        Map<String, String> response = new HashMap<>();
        response.put(USER_NAME_MAP_KEY, userName);
        response.put(TOKEN_MAP_KEY, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Logout endpoint.
     *
     * @param request  the request
     * @param response the response
     */
    @DeleteMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpStatus.NO_CONTENT.value());
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, null);
    }
}

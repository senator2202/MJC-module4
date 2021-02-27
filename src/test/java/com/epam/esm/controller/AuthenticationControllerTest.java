package com.epam.esm.controller;

import com.epam.esm.app.SpringBootRestApplication;
import com.epam.esm.model.dto.UserAuthenticationDTO;
import com.epam.esm.model.dto.UserRegistrationDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static com.epam.esm.controller.ControllerDataProvider.AUTH_HEADER;
import static com.epam.esm.controller.ControllerDataProvider.USER_TOKEN;
import static com.epam.esm.controller.ControllerDataProvider.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for AuthenticationController endpoints
 */
@SpringBootTest(classes = SpringBootRestApplication.class)
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:controller-script.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    static Stream<Arguments> argsRegisterUserInvalidData() {
        return Stream.of(
                Arguments.of(new UserRegistrationDTO(null, "siroga", "siroga", "siroga")),
                Arguments.of(new UserRegistrationDTO("", "siroga", "siroga", "siroga")),
                Arguments.of(new UserRegistrationDTO("Alexey", "", "siroga", "siroga")),
                Arguments.of(new UserRegistrationDTO("Alexey", null, "siroga", "siroga")),
                Arguments.of(new UserRegistrationDTO("Alexey", "siroga", "", "siroga")),
                Arguments.of(new UserRegistrationDTO("Alexey", "siroga", "siroga", "")),
                Arguments.of(new UserRegistrationDTO("Alexey", "siroga", null, "siroga")),
                Arguments.of(new UserRegistrationDTO("Alexey", "siroga", "siroga", null))
        );
    }

    @Test
    void authenticate() throws Exception {
        String jsonString = asJsonString(new UserAuthenticationDTO("petr", "petr"));
        mockMvc
                .perform(post("/api/auth/login")
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void authenticateBadData() throws Exception {
        String jsonString = asJsonString(new UserAuthenticationDTO("1111", "1111"));
        mockMvc
                .perform(post("/api/auth/login")
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

    @Test
    void authenticateWithValidToken() throws Exception {
        String jsonString = asJsonString(new UserAuthenticationDTO("petr", "petr"));
        mockMvc
                .perform(post("/api/auth/login")
                        .header(AUTH_HEADER, USER_TOKEN)
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void authenticateWithBadToken() throws Exception {
        String jsonString = asJsonString(new UserAuthenticationDTO("petr", "petr"));
        mockMvc
                .perform(post("/api/auth/login")
                        .header(AUTH_HEADER, "USER_TOKEN")
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

    @Test
    void registerUser() throws Exception {
        String jsonString = asJsonString(new UserRegistrationDTO("Siroga Petrovskii", "siroga", "siroga", "siroga"));
        mockMvc
                .perform(post("/api/auth/register")
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void registerUserWithValidToken() throws Exception {
        String jsonString = asJsonString(new UserRegistrationDTO("Siroga Petrovskii", "siroga", "siroga", "siroga"));
        mockMvc
                .perform(post("/api/auth/register")
                        .header(AUTH_HEADER, USER_TOKEN)
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void registerUserWithBadToken() throws Exception {
        String jsonString = asJsonString(new UserRegistrationDTO("Siroga Petrovskii", "siroga", "siroga", "siroga"));
        mockMvc
                .perform(post("/api/auth/register")
                        .header(AUTH_HEADER, "USER_TOKEN")
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

    @ParameterizedTest
    @MethodSource("argsRegisterUserInvalidData")
    void registerUserInvalidData(UserRegistrationDTO data) throws Exception {
        String jsonString = asJsonString(data);
        mockMvc
                .perform(post("/api/auth/register")
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    void logout() throws Exception {
        mockMvc
                .perform(delete("/api/auth/logout").header(AUTH_HEADER, USER_TOKEN))
                .andExpect(status().is(204));
    }

    @Test
    void logoutNoToken() throws Exception {
        mockMvc
                .perform(delete("/api/auth/logout"))
                .andExpect(status().is(302));
    }
}
package com.epam.esm.controller;

import com.epam.esm.app.SpringBootRestApplication;
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

import static com.epam.esm.controller.ControllerDataProvider.ADMIN_TOKEN;
import static com.epam.esm.controller.ControllerDataProvider.AUTH_HEADER;
import static com.epam.esm.controller.ControllerDataProvider.LIST_SIZE_JSON_SYMBOL;
import static com.epam.esm.controller.ControllerDataProvider.PAGE_PARAMETER;
import static com.epam.esm.controller.ControllerDataProvider.SIZE_PARAMETER;
import static com.epam.esm.controller.ControllerDataProvider.USER_TOKEN;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SpringBootRestApplication.class)
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:controller-script.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    static Stream<Arguments> argsFindAll() {
        return Stream.of(
                Arguments.of(ADMIN_TOKEN, null, null, 6),
                Arguments.of(USER_TOKEN, null, "5", 5),
                Arguments.of(ADMIN_TOKEN, "1", "4", 2),
                Arguments.of(USER_TOKEN, "12", "10", 0)
        );
    }

    static Stream<Arguments> argsBuyCertificate() {
        return Stream.of(
                Arguments.of("/api/users/2/orders", USER_TOKEN, "12", 200),
                Arguments.of("/api/users/2/orders", ADMIN_TOKEN, "12", 200),
                Arguments.of("/api/users/3/orders", USER_TOKEN, "12", 403),
                Arguments.of("/api/users/2/orders", "abra", "12", 401),
                Arguments.of("/api/users/2/orders", USER_TOKEN, "-12", 400),
                Arguments.of("/api/users/2/orders", USER_TOKEN, "111", 404)
        );
    }

    static Stream<Arguments> argsFindUserOrders() {
        return Stream.of(
                Arguments.of("/api/users/2/orders", USER_TOKEN, 200),
                Arguments.of("/api/users/2/orders", ADMIN_TOKEN, 200),
                Arguments.of("/api/users/3/orders", USER_TOKEN, 403),
                Arguments.of("/api/users/2/orders", "abra", 401)
        );
    }

    static Stream<Arguments> argsFindUserOrderById() {
        return Stream.of(
                Arguments.of("/api/users/2/orders/10", USER_TOKEN, 200),
                Arguments.of("/api/users/2/orders/10", ADMIN_TOKEN, 200),
                Arguments.of("/api/users/3/orders/3", USER_TOKEN, 403),
                Arguments.of("/api/users/2/orders/10", "abra", 401),
                Arguments.of("/api/users/2/orders/12", USER_TOKEN, 404)
        );
    }

    @ParameterizedTest
    @MethodSource("argsFindAll")
    void findAll(String token, String page, String size, int listSize) throws Exception {
        mockMvc
                .perform(get("/api/users")
                        .header(AUTH_HEADER, token)
                        .param(PAGE_PARAMETER, page)
                        .param(SIZE_PARAMETER, size))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LIST_SIZE_JSON_SYMBOL, hasSize(listSize)));
    }

    @Test
    void findAllNoToken() throws Exception {
        mockMvc
                .perform(get("/api/users"))
                .andExpect(status().is(403));
    }

    @Test
    void findAllInvalidToken() throws Exception {
        mockMvc
                .perform(get("/api/users")
                        .header(AUTH_HEADER, "abracadabra"))
                .andExpect(status().is(401));
    }

    @Test
    void findByIdExist() throws Exception {
        mockMvc
                .perform(get("/api/users/1").header(AUTH_HEADER, USER_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id': 1, 'name': 'Admin Adminov'}"));
    }

    @Test
    void findByIdNotExist() throws Exception {
        mockMvc
                .perform(get("/api/users/111").header(AUTH_HEADER, USER_TOKEN))
                .andExpect(status().is(404))
                .andExpect(content().json("{'errorCode': 40436}"));
    }

    @Test
    void findByIdNoToken() throws Exception {
        mockMvc
                .perform(get("/api/users/111"))
                .andExpect(status().is(403));
    }

    @Test
    void findByIdInvalidToken() throws Exception {
        mockMvc
                .perform(get("/api/users/111").header(AUTH_HEADER, "abra"))
                .andExpect(status().is(401));
    }

    @ParameterizedTest
    @MethodSource("argsBuyCertificate")
    void buyCertificate(String uri, String token, String certificateId, int statusCode) throws Exception {
        mockMvc
                .perform(post(uri)
                        .header(AUTH_HEADER, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(certificateId))
                .andExpect(status().is(statusCode));
    }

    @Test
    void buyCertificateNoToken() throws Exception {
        mockMvc
                .perform(post("/api/users/2/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("12"))
                .andExpect(status().is(403));
    }

    @ParameterizedTest
    @MethodSource("argsFindUserOrders")
    void findUserOrders(String uri, String token, int statusCode) throws Exception {
        mockMvc
                .perform(get(uri).header(AUTH_HEADER, token))
                .andExpect(status().is(statusCode));
    }

    @ParameterizedTest
    @MethodSource("argsFindUserOrderById")
    void findUserOrderById(String uri, String token, int statusCode) throws Exception {
        mockMvc
                .perform(get(uri).header(AUTH_HEADER, token))
                .andExpect(status().is(statusCode));
    }

    @Test
    void widelyUsedTag() throws Exception {
        mockMvc
                .perform(get("/api/users/widely-used-tag").header(AUTH_HEADER, ADMIN_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id': 3, 'name': 'Образование'}"));
    }

    @Test
    void widelyUsedTagForbidden() throws Exception {
        mockMvc
                .perform(get("/api/users/widely-used-tag").header(AUTH_HEADER, USER_TOKEN))
                .andExpect(status().is(403));
    }

    @Test
    void widelyUsedTagBadToken() throws Exception {
        mockMvc
                .perform(get("/api/users/widely-used-tag").header(AUTH_HEADER, "USER_TOKEN"))
                .andExpect(status().is(401));
    }

    @Test
    void widelyUsedTagNoToken() throws Exception {
        mockMvc
                .perform(get("/api/users/widely-used-tag"))
                .andExpect(status().is(403));
    }
}
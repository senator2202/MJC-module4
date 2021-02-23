package com.epam.esm.controller;

import com.epam.esm.app.SpringBootRestApplication;
import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.dto.TagDTO;
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
import static com.epam.esm.controller.ControllerDataProvider.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SpringBootRestApplication.class)
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:controller-script.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class TagApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    static Stream<Arguments> argsFindAll() {
        return Stream.of(
                Arguments.of(ADMIN_TOKEN, null, null, 16),
                Arguments.of(USER_TOKEN, null, "5", 5),
                Arguments.of(ADMIN_TOKEN, "1", "5", 5),
                Arguments.of(USER_TOKEN, "1", "10", 6),
                Arguments.of(ADMIN_TOKEN, "2", "10", 0),
                Arguments.of(USER_TOKEN, null, null, 16)
        );
    }

    @ParameterizedTest
    @MethodSource("argsFindAll")
    void findAll(String token, String page, String size, int listSize) throws Exception {
        mockMvc
                .perform(get("/api/tags")
                        .header(AUTH_HEADER, token)
                        .param(PAGE_PARAMETER, page)
                        .param(SIZE_PARAMETER, size))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LIST_SIZE_JSON_SYMBOL, hasSize(listSize)));
    }

    @Test
    void findAllNoToken() throws Exception {
        mockMvc
                .perform(get("/api/tags"))
                .andExpect(status().is(302));
    }

    @Test
    void findAllInvalidToken() throws Exception {
        mockMvc
                .perform(get("/api/tags")
                        .header(AUTH_HEADER, "abra"))
                .andExpect(status().is(401));
    }

    @Test
    void findByIdExist() throws Exception {
        mockMvc
                .perform(get("/api/tags/1").header(AUTH_HEADER, ADMIN_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':1, 'name':'Активность'}"));
    }

    @Test
    void findByIdNotExist() throws Exception {
        mockMvc
                .perform(get("/api/tags/111").header(AUTH_HEADER, ADMIN_TOKEN))
                .andExpect(status().is(404))
                .andExpect(content().json("{'errorCode': 40402}"));
    }

    @Test
    void findByIdNoToken() throws Exception {
        mockMvc
                .perform(get("/api/tags/1"))
                .andExpect(status().is(302));
    }

    @Test
    void findByIdInvalidToken() throws Exception {
        mockMvc
                .perform(get("/api/tags/1")
                        .header(AUTH_HEADER, "abra"))
                .andExpect(status().is(401));
    }


    @Test
    void createValidTag() throws Exception {
        String jsonString = asJsonString(StaticDataProvider.ADDING_TAG_DTO);
        mockMvc
                .perform(post("/api/tags")
                        .header(AUTH_HEADER, ADMIN_TOKEN)
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'name': 'Baseball'}"));
    }

    @Test
    void createNotValidTag() throws Exception {
        String jsonString = asJsonString(new TagDTO(null, ""));
        mockMvc
                .perform(post("/api/tags")
                        .header(AUTH_HEADER, ADMIN_TOKEN)
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(content().json("{'errorCode': 40002}"));
    }

    @Test
    void createTagForbidden() throws Exception {
        String jsonString = asJsonString(StaticDataProvider.ADDING_TAG_DTO);
        mockMvc
                .perform(post("/api/tags")
                        .header(AUTH_HEADER, USER_TOKEN)
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void createTagNoToken() throws Exception {
        String jsonString = asJsonString(StaticDataProvider.ADDING_TAG_DTO);
        mockMvc
                .perform(post("/api/tags")
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(302));
    }

    @Test
    void updateExisting() throws Exception {
        String jsonString = asJsonString(new TagDTO(null, "NewName"));
        mockMvc
                .perform(put("/api/tags/1")
                        .header(AUTH_HEADER, ADMIN_TOKEN)
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id': 1, 'name': 'NewName'}"));
    }

    @Test
    void updateNotExisting() throws Exception {
        String jsonString = asJsonString(new TagDTO(null, "NewName"));
        mockMvc
                .perform(put("/api/tags/111")
                        .header(AUTH_HEADER, ADMIN_TOKEN)
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andExpect(content().json("{'errorCode': 40402}"));
    }

    @Test
    void updateNotValid() throws Exception {
        String jsonString = asJsonString(new TagDTO(null, ""));
        mockMvc
                .perform(put("/api/tags/1")
                        .header(AUTH_HEADER, ADMIN_TOKEN)
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(content().json("{'errorCode': 40002}"));
    }

    @Test
    void updateForbidden() throws Exception {
        String jsonString = asJsonString(new TagDTO(null, ""));
        mockMvc
                .perform(put("/api/tags/1")
                        .header(AUTH_HEADER, USER_TOKEN)
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void updateNoToken() throws Exception {
        String jsonString = asJsonString(new TagDTO(null, ""));
        mockMvc
                .perform(put("/api/tags/1")
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(302));
    }


    @Test
    void deleteExisting() throws Exception {
        mockMvc
                .perform(delete("/api/tags/1").header(AUTH_HEADER, ADMIN_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json("{'result': true}"));
    }

    @Test
    void deleteNotExisting() throws Exception {
        mockMvc
                .perform(delete("/api/tags/1111").header(AUTH_HEADER, ADMIN_TOKEN))
                .andExpect(status().is(404))
                .andExpect(content().json("{'result': false}"));
    }

    @Test
    void deleteForbidden() throws Exception {
        mockMvc
                .perform(delete("/api/tags/1").header(AUTH_HEADER, USER_TOKEN))
                .andExpect(status().is(403));
    }

    @Test
    void deleteNoToken() throws Exception {
        mockMvc
                .perform(delete("/api/tags/1"))
                .andExpect(status().is(302));
    }
}
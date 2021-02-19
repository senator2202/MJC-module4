package com.epam.esm.controller;

import com.epam.esm.app.SpringBootRestApplication;
import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.dto.GiftCertificateDTO;
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
import static com.epam.esm.controller.ControllerDataProvider.CERTIFICATE_DESCRIPTION_PARAMETER;
import static com.epam.esm.controller.ControllerDataProvider.CERTIFICATE_NAME_PARAMETER;
import static com.epam.esm.controller.ControllerDataProvider.CERTIFICATE_TAGS_PARAMETER;
import static com.epam.esm.controller.ControllerDataProvider.DIRECTION_PARAMETER;
import static com.epam.esm.controller.ControllerDataProvider.LIST_SIZE_JSON_SYMBOL;
import static com.epam.esm.controller.ControllerDataProvider.PAGE_PARAMETER;
import static com.epam.esm.controller.ControllerDataProvider.SIZE_PARAMETER;
import static com.epam.esm.controller.ControllerDataProvider.SORT_PARAMETER;
import static com.epam.esm.controller.ControllerDataProvider.USER_TOKEN;
import static com.epam.esm.controller.ControllerDataProvider.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SpringBootRestApplication.class)
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:controller-script.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class GiftCertificateApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    static Stream<Arguments> argsFindAllValidParameters() {
        return Stream.of(
                Arguments.of(ADMIN_TOKEN, null, null, null, null, null, null, null, 13),
                Arguments.of(USER_TOKEN, null, null, null, null, null, null, "10", 10),
                Arguments.of(ADMIN_TOKEN, null, null, null, "price", null, null, null, 13),
                Arguments.of(USER_TOKEN, null, null, null, "price", "asc", null, null, 13),
                Arguments.of(ADMIN_TOKEN, null, null, null, "price", "desc", null, null, 13),
                Arguments.of(USER_TOKEN, null, null, null, null, "asc", null, null, 13),
                Arguments.of(ADMIN_TOKEN, null, null, null, null, null, "0", "10", 10),
                Arguments.of(USER_TOKEN, null, null, null, null, null, "1", "10", 3),
                Arguments.of(ADMIN_TOKEN, "тату", null, null, null, null, null, null, 2),
                Arguments.of(USER_TOKEN, null, "бесплатн", null, null, null, null, null, 5),
                Arguments.of(ADMIN_TOKEN, null, null, "Активность", null, null, null, null, 3),
                Arguments.of(USER_TOKEN, null, null, "Активность,Отдых", null, null, null, null, 2),
                Arguments.of(ADMIN_TOKEN, "на", null, "Активность,Отдых", null, null, null, null, 2)
        );
    }

    static Stream<Arguments> argsFindAllNotValidParameters() {
        return Stream.of(
                Arguments.of("", null, null, null, null, null, null),
                Arguments.of("11111111111111111111111111111111111111111111111111111111111111" +
                        "11111111111111111111111111111111111111", null, null, null, null, null, null),
                Arguments.of(null, "", null, null, null, null, null),
                Arguments.of(null, "11111111111111111111111111111111111111111111111111111111111111111111111" +
                        "11111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                        "11111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                        "111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                        "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                        "1111111111111111111111111111111111111111111111111111111111111", null, null, null, null, null),
                Arguments.of(null, null, "", null, null, null, null),
                Arguments.of(null, null, "1111111111111111111111111111111111111111111111111111111111111111111" +
                        "111111111111111111111111111111111111111,2222", null, null, null, null),
                Arguments.of(null, null, null, "", null, null, null),
                Arguments.of(null, null, null, "abra", null, null, null),
                Arguments.of(null, null, null, "duration", "", null, null),
                Arguments.of(null, null, null, "duration", "abra", null, null),
                Arguments.of(null, null, null, null, null, "-25", null),
                Arguments.of(null, null, null, null, null, null, "-25"),
                Arguments.of(null, null, null, null, null, "25", "-25")
        );
    }

    @ParameterizedTest
    @MethodSource("argsFindAllValidParameters")
    void findAllValidParameters(String token, String name, String description, String tagNames, String sortType,
                                String direction, String page, String size, int listSize) throws Exception {
        mockMvc
                .perform(get("/api/certificates")
                        .header(AUTH_HEADER, token)
                        .param(PAGE_PARAMETER, page)
                        .param(SIZE_PARAMETER, size)
                        .param(CERTIFICATE_NAME_PARAMETER, name)
                        .param(CERTIFICATE_DESCRIPTION_PARAMETER, description)
                        .param(CERTIFICATE_TAGS_PARAMETER, tagNames)
                        .param(SORT_PARAMETER, sortType)
                        .param(DIRECTION_PARAMETER, direction))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LIST_SIZE_JSON_SYMBOL, hasSize(listSize)));
    }

    @ParameterizedTest
    @MethodSource("argsFindAllNotValidParameters")
    void findAllNotValidParameters(String name, String description, String tagNames, String sortType,
                                   String direction, String page, String size) throws Exception {
        mockMvc
                .perform(get("/api/certificates")
                        .header(AUTH_HEADER, ADMIN_TOKEN)
                        .param(PAGE_PARAMETER, page)
                        .param(SIZE_PARAMETER, size)
                        .param(CERTIFICATE_NAME_PARAMETER, name)
                        .param(CERTIFICATE_DESCRIPTION_PARAMETER, description)
                        .param(CERTIFICATE_TAGS_PARAMETER, tagNames)
                        .param(SORT_PARAMETER, sortType)
                        .param(DIRECTION_PARAMETER, direction))
                .andExpect(status().is(400))
                .andExpect(content().json("{'errorCode': 40001}"));
    }

    @Test
    void findAllNotToken() throws Exception {
        mockMvc
                .perform(get("/api/certificates"))
                .andExpect(status().is(200))
                .andExpect(jsonPath(LIST_SIZE_JSON_SYMBOL, hasSize(13)));
    }

    @Test
    void findAllInvalidToken() throws Exception {
        mockMvc
                .perform(get("/api/certificates").header(AUTH_HEADER, "abra"))
                .andExpect(status().is(401));
    }

    @Test
    void findByIdExist() throws Exception {
        mockMvc
                .perform(get("/api/certificates/1").header(AUTH_HEADER, ADMIN_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':1, 'name':'Сауна Тритон'}"));
    }

    @Test
    void findByIdNotExist() throws Exception {
        mockMvc
                .perform(get("/api/certificates/111").header(AUTH_HEADER, USER_TOKEN))
                .andExpect(status().is(404))
                .andExpect(content().json("{'errorCode': 40401}"));
    }

    @Test
    void findByIdNoToken() throws Exception {
        mockMvc
                .perform(get("/api/certificates/1"))
                .andExpect(status().is(200))
                .andExpect(content().json("{'id':1, 'name':'Сауна Тритон'}"));
    }

    @Test
    void createValidCertificate() throws Exception {
        String jsonString = asJsonString(StaticDataProvider.ADDING_GIFT_CERTIFICATE);
        mockMvc
                .perform(post("/api/certificates")
                        .header(AUTH_HEADER, ADMIN_TOKEN)
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'name': 'English courses', 'duration': 180, 'price': 250.00}"));
    }

    @Test
    void createNotValidCertificate() throws Exception {
        String jsonString = asJsonString(new GiftCertificateDTO());
        mockMvc
                .perform(post("/api/certificates")
                        .header(AUTH_HEADER, ADMIN_TOKEN)
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(content().json("{'errorCode': 40003}"));
    }

    @Test
    void createCertificateForbidden() throws Exception {
        String jsonString = asJsonString(StaticDataProvider.ADDING_GIFT_CERTIFICATE);
        mockMvc
                .perform(post("/api/certificates")
                        .header(AUTH_HEADER, USER_TOKEN)
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void createCertificateNoToken() throws Exception {
        String jsonString = asJsonString(StaticDataProvider.ADDING_GIFT_CERTIFICATE);
        mockMvc
                .perform(post("/api/certificates")
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void updateExisting() throws Exception {
        String jsonString = asJsonString(new GiftCertificateDTO(null, "NewName", null, null, null, null, null, null));
        mockMvc
                .perform(patch("/api/certificates/1")
                        .header(AUTH_HEADER, ADMIN_TOKEN)
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id': 1, 'name': 'NewName'}"));
    }

    @Test
    void updateNotExisting() throws Exception {
        String jsonString = asJsonString(new GiftCertificateDTO(null, "NewName", null, null, null, null, null, null));
        mockMvc
                .perform(patch("/api/certificates/111")
                        .header(AUTH_HEADER, ADMIN_TOKEN)
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andExpect(content().json("{'errorCode': 40401}"));
    }

    @Test
    void updateNotValid() throws Exception {
        String jsonString = asJsonString(new GiftCertificateDTO(null, "", null, null, null, null, null, null));
        mockMvc
                .perform(patch("/api/certificates/1")
                        .header(AUTH_HEADER, ADMIN_TOKEN)
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(content().json("{'errorCode': 40003}"));
    }

    @Test
    void updateForbidden() throws Exception {
        String jsonString = asJsonString(new GiftCertificateDTO(null, "NewName", null, null, null, null, null, null));
        mockMvc
                .perform(patch("/api/certificates/1")
                        .header(AUTH_HEADER, USER_TOKEN)
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void updateNoToken() throws Exception {
        String jsonString = asJsonString(new GiftCertificateDTO(null, "NewName", null, null, null, null, null, null));
        mockMvc
                .perform(patch("/api/certificates/1")
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    void deleteExisting() throws Exception {
        mockMvc
                .perform(delete("/api/certificates/1").header(AUTH_HEADER, ADMIN_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json("{'result': true}"));
    }

    @Test
    void deleteNotExisting() throws Exception {
        mockMvc
                .perform(delete("/api/certificates/1111").header(AUTH_HEADER, ADMIN_TOKEN))
                .andExpect(status().is(404))
                .andExpect(content().json("{'result': false}"));
    }

    @Test
    void deleteForbidden() throws Exception {
        mockMvc
                .perform(delete("/api/certificates/1").header(AUTH_HEADER, USER_TOKEN))
                .andExpect(status().is(403));
    }

    @Test
    void deleteNoToken() throws Exception {
        mockMvc
                .perform(delete("/api/certificates/1"))
                .andExpect(status().is(403));
    }
}
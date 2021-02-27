package com.epam.esm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class, providing test data and methods for Controller layer
 */
public class ControllerDataProvider {

    public static final String PAGE_PARAMETER = "page";
    public static final String SIZE_PARAMETER = "size";
    public static final String CERTIFICATE_NAME_PARAMETER = "name";
    public static final String CERTIFICATE_DESCRIPTION_PARAMETER = "description";
    public static final String CERTIFICATE_TAGS_PARAMETER = "tags";
    public static final String SORT_PARAMETER = "sort";
    public static final String DIRECTION_PARAMETER = "direction";
    public static final String LIST_SIZE_JSON_SYMBOL = "$";

    public static final String AUTH_HEADER = "Authorization";
    public static final String ADMIN_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYxNDQyMzE5Niw" +
            "iZXhwIjoxNjc0OTAzMTk2fQ.zsDEuGh-76cs200pxlTkN-TsqZOiwiktFJEmxuOmNFQ";
    public static final String USER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXRyIiwiaWF0IjoxNjE0NDIzMTY1LC" +
            "JleHAiOjE2NzQ5MDMxNjV9.gsUyPWd7yGDynbI0ELP9hwAvyk2EY2n9e43yZOIpkOE";

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

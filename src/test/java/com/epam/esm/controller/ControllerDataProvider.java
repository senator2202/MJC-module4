package com.epam.esm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

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
    public static final String ADMIN_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYxMzc1MTEzN" +
            "iwiZXhwIjoxNjc0MjMxMTM2fQ.6nji6PR5JIEdRrWI9VN6zyjd6zLUvwEHb1m2dA0xoxE";
    public static final String USER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXRyIiwiaWF0IjoxNjEzNzY4NTcyL" +
            "CJleHAiOjE2MTQzNzMzNzJ9.yrUHgXM7hrO2paIik_PPy0hxELQANWpCgw43aX9U-F4";

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

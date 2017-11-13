package com.acuo.common.json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public final class JSONUtils {

    private JSONUtils() {
    }

    public static boolean isValid(String json) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
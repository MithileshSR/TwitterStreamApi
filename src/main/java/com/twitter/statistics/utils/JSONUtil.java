package com.twitter.statistics.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * A utility class to de-serialize Json string into Java model using Jackson.
 * 
 * @author Mithilesh Ravindran
 *
 * @param <T>
 */
public final class JSONUtil<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public T convertToObject(String jsonString, Class<T> type)
            throws JsonParseException, JsonMappingException, IOException {

        T object = objectMapper.readValue(jsonString, type);
        return object;
    }
}

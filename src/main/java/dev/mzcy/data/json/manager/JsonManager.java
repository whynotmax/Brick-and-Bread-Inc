package dev.mzcy.data.json.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;

/**
 * JsonManager is a utility class for managing JSON serialization and deserialization using Jackson.
 * It provides a pre-configured ObjectMapper instance that can be used throughout the application.
 */
public class JsonManager {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Returns the pre-configured ObjectMapper instance.
     *
     * @return the ObjectMapper instance
     */
    public static ObjectMapper get() {
        return MAPPER;
    }
}
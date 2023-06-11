package com.spotlight.platform.userprofile.api.core.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.dropwizard.jackson.Jackson;

/**
 * Utility class for creating and configuring an instance of {@link ObjectMapper}.
 */
public class JsonMapper {
    private static final ObjectMapper MAPPER_INSTANCE = createInstance();

    private JsonMapper() {
    }

    /**
     * Creates a new instance of {@link ObjectMapper} and applies the necessary configuration.
     *
     * @return The configured instance of {@link ObjectMapper}.
     */
    public static ObjectMapper createInstance() {
        var objectMapper = Jackson.newObjectMapper();
        toggleFeatures(objectMapper);
        setVisibilities(objectMapper);
        return objectMapper;
    }

    /**
     * Retrieves the singleton instance of {@link ObjectMapper}.
     *
     * @return The singleton instance of {@link ObjectMapper}.
     */
    public static ObjectMapper getInstance() {
        return MAPPER_INSTANCE;
    }

    /**
     * Toggles specific serialization and deserialization features of the given {@link ObjectMapper}.
     *
     * @param objectMapper The {@link ObjectMapper} to configure.
     */
    public static void toggleFeatures(ObjectMapper objectMapper) {
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    /**
     * Sets the visibility rules for property auto-detection of the given {@link ObjectMapper}.
     *
     * @param objectMapper The {@link ObjectMapper} to configure.
     */
    public static void setVisibilities(ObjectMapper objectMapper) {
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.ANY);
    }
}

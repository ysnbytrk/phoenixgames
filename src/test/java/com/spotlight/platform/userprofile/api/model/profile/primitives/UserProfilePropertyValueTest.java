package com.spotlight.platform.userprofile.api.model.profile.primitives;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class UserProfilePropertyValueTest {
    private static final String STRING_VALUE = "someString";
    private static final int INTEGER_VALUE = 5;
    private static final List<String> LIST_VALUE = List.of("one", "two");

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void equals_ReturnsTrueForEqualValues() {
        assertThat(UserProfilePropertyValue.valueOf(STRING_VALUE)).isEqualTo(UserProfilePropertyValue.valueOf(STRING_VALUE));
        assertThat(UserProfilePropertyValue.valueOf(INTEGER_VALUE)).isEqualTo(UserProfilePropertyValue.valueOf(INTEGER_VALUE));
        assertThat(UserProfilePropertyValue.valueOf(LIST_VALUE)).isEqualTo(UserProfilePropertyValue.valueOf(LIST_VALUE));
    }

    @Test
    void serialization_WorksCorrectly() throws Exception {
        String stringJson = objectMapper.writeValueAsString(UserProfilePropertyValue.valueOf(STRING_VALUE));
        String integerJson = objectMapper.writeValueAsString(UserProfilePropertyValue.valueOf(INTEGER_VALUE));
        String listJson = objectMapper.writeValueAsString(UserProfilePropertyValue.valueOf(LIST_VALUE));

        assertThat(stringJson).isEqualTo("\"someString\"");
        assertThat(integerJson).isEqualTo("5");
        assertThat(listJson).isEqualTo("[\"one\",\"two\"]");
    }
}

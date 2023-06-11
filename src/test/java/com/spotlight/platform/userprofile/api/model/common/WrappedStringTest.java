package com.spotlight.platform.userprofile.api.model.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class WrappedStringTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testEqualityWithSameType() {
        SomeId idA = new SomeId("a");
        SomeId idB = new SomeId("b");

        assertThat(idA.equals(idA)).isTrue();
        assertThat(idA.equals(idB)).isFalse();
    }

    @Test
    void testEqualityWithDifferentType() {
        SomeId idA = new SomeId("a");
        AnotherId idB = new AnotherId("a");

        assertThat(idA.equals(idB)).isFalse();
    }

    @Test
    void testEqualityWithNull() {
        SomeId idA = new SomeId("a");

        assertThat(idA.equals(null)).isFalse();
    }

    @Test
    void testSerialization() throws Exception {
        SomeId idA = new SomeId("a");

        assertThat(objectMapper.writeValueAsString(idA)).isEqualTo("\"a\"");
    }

    @Test
    void testDeserialization() {
        SomeId idA = new SomeId("a");

        assertThat(objectMapper.convertValue("a", SomeId.class)).isEqualTo(idA);
    }

    @Test
    void testToString() {
        SomeId idA = new SomeId("a");

        assertThat(idA).hasToString("a");
    }

    private static class SomeId extends WrappedString {
        public SomeId(String value) {
            super(value);
        }
    }

    private static class AnotherId extends WrappedString {
        public AnotherId(String value) {
            super(value);
        }
    }
}

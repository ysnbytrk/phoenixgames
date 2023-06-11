package com.spotlight.platform.userprofile.api.model.common;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents a wrapped string that provides a base implementation for string value objects.
 * This class serves as an abstraction for creating custom string value classes.
 */
public abstract class WrappedString {

    private final String value;

    /**
     * Constructs a WrappedString object with the provided string value.
     *
     * @param value The string value.
     */
    protected WrappedString(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value of the wrapped string.
     *
     * @return The value of the string.
     */
    @JsonValue
    protected String getValue() {
        return value;
    }

    /**
     * Returns the string representation of the wrapped string.
     *
     * @return The string representation of the value.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Generates the hash code for the wrapped string.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Compares the wrapped string with another object for equality.
     *
     * @param obj The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        return value.equals(((WrappedString) obj).value);
    }
}

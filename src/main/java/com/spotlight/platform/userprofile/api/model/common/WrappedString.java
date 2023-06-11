package com.spotlight.platform.userprofile.api.model.common;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents a wrapped string value.
 * Subclasses can extend this class to provide specialized behavior for specific string types.
 */
public abstract class WrappedString {

    private final String value;

    /**
     * Constructs a new WrappedString object with the specified value.
     *
     * @param value The string value.
     */
    protected WrappedString(String value) {
        this.value = value;
    }

    /**
     * Retrieves the value of the WrappedString.
     *
     * @return The string value.
     */
    @JsonValue
    protected String getValue() {
        return value;
    }

    /**
     * Returns the string representation of the WrappedString.
     *
     * @return The string value.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Computes the hash code of the WrappedString.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Checks if this WrappedString is equal to the specified object.
     *
     * @param obj The object to compare against.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        return value.equals(((WrappedString) obj).value);
    }
}

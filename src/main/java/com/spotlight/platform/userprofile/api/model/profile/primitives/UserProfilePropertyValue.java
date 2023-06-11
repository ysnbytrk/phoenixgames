package com.spotlight.platform.userprofile.api.model.profile.primitives;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/**
 * Represents the value of a user profile property.
 */
public class UserProfilePropertyValue {

    private final Object value;

    /**
     * Constructs a new UserProfilePropertyValue object with the specified value.
     *
     * @param value The value of the user profile property.
     */
    @JsonCreator
    private UserProfilePropertyValue(Object value) {
        this.value = value;
    }

    /**
     * Creates a UserProfilePropertyValue object with the specified value.
     *
     * @param value The value of the user profile property.
     * @return The created UserProfilePropertyValue object.
     */
    public static UserProfilePropertyValue valueOf(Object value) {
        return new UserProfilePropertyValue(value);
    }

    /**
     * Retrieves the value of the user profile property.
     *
     * @return The value of the user profile property.
     */
    @JsonValue
    public Object getValue() {
        return value;
    }

    /**
     * Checks if this UserProfilePropertyValue is equal to another object.
     * Two UserProfilePropertyValue objects are considered equal if their values are equal.
     *
     * @param o The object to compare to.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfilePropertyValue that = (UserProfilePropertyValue) o;
        return Objects.equals(value, that.value);
    }

    /**
     * Generates the hash code for this UserProfilePropertyValue.
     *
     * @return The hash code value for this UserProfilePropertyValue.
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

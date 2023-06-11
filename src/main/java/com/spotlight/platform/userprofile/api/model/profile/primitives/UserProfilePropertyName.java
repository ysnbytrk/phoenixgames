package com.spotlight.platform.userprofile.api.model.profile.primitives;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.spotlight.platform.userprofile.api.model.common.AlphaNumericalStringWithMaxLength;

/**
 * Represents the name of a user profile property.
 * The UserProfilePropertyName class extends AlphaNumericalStringWithMaxLength to enforce a specific format and length for the property name.
 */
public class UserProfilePropertyName extends AlphaNumericalStringWithMaxLength implements Comparable<UserProfilePropertyName> {

    /**
     * Creates a new UserProfilePropertyName object with the specified value.
     *
     * @param value The string value of the property name.
     */
    @JsonCreator
    protected UserProfilePropertyName(String value) {
        super(value);
    }

    /**
     * Creates a UserProfilePropertyName object from the given string representation of the property name.
     *
     * @param value The string representation of the property name.
     * @return The created UserProfilePropertyName object.
     */
    public static UserProfilePropertyName valueOf(String value) {
        return new UserProfilePropertyName(value);
    }

    /**
     * Compares this UserProfilePropertyName with another UserProfilePropertyName for ordering.
     * The comparison is based on the lexicographic order of the property names.
     *
     * @param o The UserProfilePropertyName to compare to.
     * @return A negative integer if this property name is less than the other, zero if they are equal,
     *         or a positive integer if this property name is greater than the other.
     */
    @Override
    public int compareTo(UserProfilePropertyName o) {
        return getValue().compareTo(o.getValue());
    }

    /**
     * Checks if this UserProfilePropertyName is equal to another object.
     * Two UserProfilePropertyName objects are considered equal if their values are equal.
     *
     * @param obj The object to compare to.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * Generates the hash code for this UserProfilePropertyName.
     *
     * @return The hash code value for this UserProfilePropertyName.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

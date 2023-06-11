package com.spotlight.platform.userprofile.api.model.profile.primitives;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.spotlight.platform.userprofile.api.model.common.AlphaNumericalStringWithMaxLength;

/**
 * Represents the name of a user profile property.
 * Extends the AlphaNumericalStringWithMaxLength class and implements Comparable interface.
 */
public class UserProfilePropertyName extends AlphaNumericalStringWithMaxLength implements Comparable<UserProfilePropertyName> {

    /**
     * Constructs a new UserProfilePropertyName object with the specified value.
     *
     * @param value The value of the user profile property name.
     */
    @JsonCreator
    protected UserProfilePropertyName(String value) {
        super(value);
    }

    /**
     * Creates a UserProfilePropertyName object from the specified string value.
     *
     * @param value The string representation of the user profile property name.
     * @return The created UserProfilePropertyName object.
     */
    public static UserProfilePropertyName valueOf(String value) {
        return new UserProfilePropertyName(value);
    }

    /**
     * Compares this UserProfilePropertyName with another UserProfilePropertyName for order.
     *
     * @param o The UserProfilePropertyName to be compared.
     * @return A negative integer, zero, or a positive integer as this UserProfilePropertyName is less than,
     *         equal to, or greater than the specified UserProfilePropertyName.
     */
    @Override
    public int compareTo(UserProfilePropertyName o) {
        return getValue().compareTo(o.getValue());
    }

    /**
     * Checks if this UserProfilePropertyName is equal to another object.
     *
     * @param obj The object to compare against.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * Returns the hash code value for this UserProfilePropertyName.
     *
     * @return The hash code value for this UserProfilePropertyName.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

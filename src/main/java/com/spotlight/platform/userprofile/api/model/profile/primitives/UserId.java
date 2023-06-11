package com.spotlight.platform.userprofile.api.model.profile.primitives;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.spotlight.platform.userprofile.api.model.common.AlphaNumericalStringWithMaxLength;

/**
 * Represents a unique identifier for a user profile.
 * Extends the AlphaNumericalStringWithMaxLength class.
 */
public class UserId extends AlphaNumericalStringWithMaxLength {

    /**
     * Constructs a new UserId object with the specified value.
     *
     * @param value The value of the user ID.
     */
    @JsonCreator
    protected UserId(String value) {
        super(value);
    }

    /**
     * Creates a UserId object from the specified string representation.
     *
     * @param userId The string representation of the user ID.
     * @return The created UserId object.
     */
    public static UserId valueOf(String userId) {
        return new UserId(userId);
    }
}

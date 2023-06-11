package com.spotlight.platform.userprofile.api.model.profile.primitives;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.spotlight.platform.userprofile.api.model.common.AlphaNumericalStringWithMaxLength;

/**
 * Represents the unique identifier of a user.
 * The UserId class extends AlphaNumericalStringWithMaxLength to enforce a specific format and length for the user identifier.
 */
public class UserId extends AlphaNumericalStringWithMaxLength {

    /**
     * Creates a new UserId object with the specified value.
     *
     * @param value The string value of the user identifier.
     */
    @JsonCreator
    protected UserId(String value) {
        super(value);
    }

    /**
     * Creates a UserId object from the given string representation of the user identifier.
     *
     * @param userId The string representation of the user identifier.
     * @return The created UserId object.
     */
    public static UserId valueOf(String userId) {
        return new UserId(userId);
    }
}

package com.spotlight.platform.userprofile.api.model.profile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a user profile containing various properties.
 */
public record UserProfile(
        @JsonProperty UserId userId,
        @JsonProperty @JsonFormat(shape = JsonFormat.Shape.STRING) Instant latestUpdateTime,
        @JsonProperty Map<UserProfilePropertyName, UserProfilePropertyValue> userProfileProperties
) {
    /**
     * Creates a new UserProfile object with the given user profile property.
     *
     * @param propertyName  The name of the user profile property.
     * @param propertyValue The value of the user profile property.
     * @return A new UserProfile object with the updated user profile property.
     */
    public UserProfile withUserProfileProperty(UserProfilePropertyName propertyName, UserProfilePropertyValue propertyValue) {
        Map<UserProfilePropertyName, UserProfilePropertyValue> updatedProperties = new HashMap<>(userProfileProperties);
        updatedProperties.put(propertyName, propertyValue);
        return new UserProfile(userId, OffsetDateTime.now().toInstant(), updatedProperties);
    }

    /**
     * Adds the given values to a list-type user profile property.
     *
     * @param propertyName The name of the user profile property.
     * @param valuesToAdd  The values to add to the user profile property.
     * @return A new UserProfile object with the updated user profile property.
     */
    public UserProfile collectUserProfileProperty(UserProfilePropertyName propertyName, List<String> valuesToAdd) {
        Map<UserProfilePropertyName, UserProfilePropertyValue> updatedProperties = new HashMap<>(this.userProfileProperties);
        UserProfilePropertyValue newPropertyValue = UserProfilePropertyValue.valueOf(valuesToAdd);
        updatedProperties.put(propertyName, newPropertyValue);
        return new UserProfile(this.userId, this.latestUpdateTime, updatedProperties);
    }

    /**
     * Increments the value of an integer-type user profile property.
     *
     * @param propertyName   The name of the user profile property.
     * @param incrementValue The value to increment the user profile property by.
     * @return A new UserProfile object with the updated user profile property.
     */
    public UserProfile incrementUserProfileProperty(UserProfilePropertyName propertyName, int incrementValue) {
        Map<UserProfilePropertyName, UserProfilePropertyValue> updatedProperties = new HashMap<>(userProfileProperties);
        UserProfilePropertyValue currentValue = userProfileProperties.getOrDefault(propertyName, UserProfilePropertyValue.valueOf(0));
        int currentValueInt = parseIntegerValue(currentValue);
        int updatedValueInt = currentValueInt + incrementValue;
        UserProfilePropertyValue updatedValue = UserProfilePropertyValue.valueOf(updatedValueInt);
        updatedProperties.put(propertyName, updatedValue);
        return new UserProfile(userId, OffsetDateTime.now().toInstant(), updatedProperties);
    }

    /**
     * Parses the integer value from the given UserProfilePropertyValue.
     *
     * @param userProfilePropertyValue The UserProfilePropertyValue to parse the integer value from.
     * @return The parsed integer value.
     * @throws IllegalArgumentException if the UserProfilePropertyValue value is not an Integer or Long.
     */
    private int parseIntegerValue(UserProfilePropertyValue userProfilePropertyValue) {
        Object value = userProfilePropertyValue.getValue();
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Long) {
            return Math.toIntExact((Long) value);
        } else {
            throw new IllegalArgumentException("Invalid property value type. Expected Integer or Long.");
        }
    }
}

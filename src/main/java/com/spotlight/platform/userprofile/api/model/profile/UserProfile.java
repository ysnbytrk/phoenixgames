package com.spotlight.platform.userprofile.api.model.profile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.*;

/**
 * Represents a user profile containing various properties.
 */
public record UserProfile(
        @JsonProperty UserId userId,
        @JsonProperty @JsonFormat(shape = JsonFormat.Shape.STRING) Instant latestUpdateTime,
        @JsonProperty Map<UserProfilePropertyName, UserProfilePropertyValue> userProfileProperties) {

    /**
     * Creates a new UserProfile object with the specified user ID, latest update time, and user profile properties.
     *
     * @param userId                  The user ID associated with the profile.
     * @param latestUpdateTime        The latest update time of the profile.
     * @param userProfileProperties   The map of user profile properties.
     * @return                        The created UserProfile object.
     */
    public UserProfile withUserProfileProperty(UserProfilePropertyName propertyName, UserProfilePropertyValue propertyValue) {
        Map<UserProfilePropertyName, UserProfilePropertyValue> updatedProperties = new HashMap<>(userProfileProperties);
        updatedProperties.put(propertyName, propertyValue);
        return new UserProfile(userId, OffsetDateTime.now().toInstant(), updatedProperties);
    }

    /**
     * Creates a new UserProfile object with the specified user ID, latest update time, and user profile properties
     * where the property is a list that collects additional values.
     *
     * @param propertyName   The name of the profile property.
     * @param valuesToAdd    The list of values to add to the profile property.
     * @return               The created UserProfile object.
     */
    public UserProfile collectUserProfileProperty(UserProfilePropertyName propertyName, List<String> valuesToAdd) {
        Map<UserProfilePropertyName, UserProfilePropertyValue> updatedProperties = new HashMap<>(this.userProfileProperties);
        UserProfilePropertyValue newPropertyValue = UserProfilePropertyValue.valueOf(valuesToAdd);
        updatedProperties.put(propertyName, newPropertyValue);
        return new UserProfile(this.userId, this.latestUpdateTime, updatedProperties);
    }

    /**
     * Creates a new UserProfile object with the specified user ID, latest update time, and user profile properties
     * where the property is incremented by the specified value.
     *
     * @param propertyName     The name of the profile property.
     * @param incrementValue   The value to increment the profile property by.
     * @return                 The created UserProfile object.
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
     * Parses an integer value from the UserProfilePropertyValue.
     *
     * @param userProfilePropertyValue   The UserProfilePropertyValue to parse.
     * @return                          The parsed integer value.
     * @throws IllegalArgumentException if the UserProfilePropertyValue value is not of type Integer or Long.
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

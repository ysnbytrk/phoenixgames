package com.spotlight.platform.userprofile.api.model.profile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.*;

public record UserProfile(@JsonProperty UserId userId,
                          @JsonProperty @JsonFormat(shape = JsonFormat.Shape.STRING) Instant latestUpdateTime,
                          @JsonProperty Map<UserProfilePropertyName, UserProfilePropertyValue> userProfileProperties) {


    public UserProfile withUserProfileProperty(UserProfilePropertyName propertyName, UserProfilePropertyValue propertyValue) {
        Map<UserProfilePropertyName, UserProfilePropertyValue> updatedProperties = new HashMap<>(userProfileProperties);
        updatedProperties.put(propertyName, propertyValue);
        return new UserProfile(userId, OffsetDateTime.now().toInstant(), updatedProperties);
    }

    public UserProfile collectUserProfileProperty(UserProfilePropertyName propertyName, List<String> valuesToAdd) {
        Map<UserProfilePropertyName, UserProfilePropertyValue> updatedProperties = new HashMap<>(this.userProfileProperties);
        UserProfilePropertyValue newPropertyValue = UserProfilePropertyValue.valueOf(valuesToAdd);
        updatedProperties.put(propertyName, newPropertyValue);
        return new UserProfile(this.userId, this.latestUpdateTime, updatedProperties);
    }


    public UserProfile incrementUserProfileProperty(UserProfilePropertyName propertyName, int incrementValue) {
        Map<UserProfilePropertyName, UserProfilePropertyValue> updatedProperties = new HashMap<>(userProfileProperties);
        UserProfilePropertyValue currentValue = userProfileProperties.getOrDefault(propertyName, UserProfilePropertyValue.valueOf(0));
        int currentValueInt = parseIntegerValue(currentValue);
        int updatedValueInt = currentValueInt + incrementValue;
        UserProfilePropertyValue updatedValue = UserProfilePropertyValue.valueOf(updatedValueInt);
        updatedProperties.put(propertyName, updatedValue);
        return new UserProfile(userId, OffsetDateTime.now().toInstant(), updatedProperties);
    }

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

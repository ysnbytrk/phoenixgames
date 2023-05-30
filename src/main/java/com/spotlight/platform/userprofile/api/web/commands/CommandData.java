package com.spotlight.platform.userprofile.api.web.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import java.util.Map;

/**
 * Represents the data of a command to be executed on the user profile.
 */
public record CommandData(UserId userId, CommandType type,
                          Map<UserProfilePropertyName, UserProfilePropertyValue> properties) {
    @JsonCreator
    public CommandData(@JsonProperty("userId") UserId userId,
                       @JsonProperty("type") CommandType type,
                       @JsonProperty("properties") Map<UserProfilePropertyName, UserProfilePropertyValue> properties) {
        this.userId = userId;
        this.type = type;
        this.properties = properties;
    }

    @Override
    @JsonProperty("userId")
    public UserId userId() {
        return userId;
    }

    @Override
    @JsonProperty("type")
    public CommandType type() {
        return type;
    }

    @Override
    @JsonProperty("properties")
    public Map<UserProfilePropertyName, UserProfilePropertyValue> properties() {
        return properties;
    }
}

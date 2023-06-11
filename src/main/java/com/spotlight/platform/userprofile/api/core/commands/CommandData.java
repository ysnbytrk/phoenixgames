package com.spotlight.platform.userprofile.api.core.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import java.util.Map;

/**
 * Represents the data of a command to be executed on the user profile.
 */
public record CommandData(
        @JsonProperty("userId") UserId userId,
        @JsonProperty("type") CommandType type,
        @JsonProperty("properties") Map<UserProfilePropertyName, UserProfilePropertyValue> properties) {

    /**
     * Constructs a new instance of CommandData.
     *
     * @param userId     The ID of the user associated with the command.
     * @param type       The type of the command.
     * @param properties The properties associated with the command.
     */
    @JsonCreator
    public CommandData {
    }

    /**
     * Retrieves the user ID associated with the command.
     *
     * @return The user ID.
     */
    @JsonProperty("userId")
    public UserId userId() {
        return userId;
    }

    /**
     * Retrieves the type of the command.
     *
     * @return The command type.
     */
    @JsonProperty("type")
    public CommandType type() {
        return type;
    }

    /**
     * Retrieves the properties associated with the command.
     *
     * @return The command properties.
     */
    @JsonProperty("properties")
    public Map<UserProfilePropertyName, UserProfilePropertyValue> properties() {
        return properties;
    }
}

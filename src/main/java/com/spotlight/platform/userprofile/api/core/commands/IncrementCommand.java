package com.spotlight.platform.userprofile.api.core.commands;

import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Represents an increment command to update properties in the user profile.
 */
public record IncrementCommand(UserProfile userProfile) implements Command {

    /**
     * Creates an increment command with the specified user profile properties.
     *
     * @param userId          The user ID.
     * @param profileProperty The map of profile properties to increment.
     */
    public IncrementCommand(UserId userId, Map<UserProfilePropertyName, UserProfilePropertyValue> profileProperty) {
        this(new UserProfile(userId, OffsetDateTime.now().toInstant(), profileProperty));
    }

    /**
     * Retrieves the user profile to be incremented.
     *
     * @return The user profile.
     */
    public UserProfile getUserProfile() {
        return userProfile;
    }

    @Override
    public CommandType getType() {
        return CommandType.INCREMENT;
    }

    @Override
    public UserId getUserId() {
        return getUserProfile().userId();
    }


}
package com.spotlight.platform.userprofile.api.core.commands;

import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Command to replace the properties of a user profile.
 */
public record ReplaceCommand(UserProfile userProfile) implements Command {

    /**
     * Creates a replace command with the specified user profile properties.
     *
     * @param userId          The user ID.
     * @param profileProperty The map of profile properties to replace.
     */
    public ReplaceCommand(UserId userId, Map<UserProfilePropertyName, UserProfilePropertyValue> profileProperty) {
        this(new UserProfile(userId, OffsetDateTime.now().toInstant(), profileProperty));
    }

    /**
     * Retrieves the user profile to be replaced.
     *
     * @return The user profile.
     */
    public UserProfile getUserProfile() {
        return userProfile;
    }

    @Override
    public CommandType getType() {
        return CommandType.REPLACE;
    }

    @Override
    public UserId getUserId() {
        return getUserProfile().userId();
    }

}

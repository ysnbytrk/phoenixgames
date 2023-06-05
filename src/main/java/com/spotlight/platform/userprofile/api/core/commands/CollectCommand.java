package com.spotlight.platform.userprofile.api.core.commands;

import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Command to collect values to a list in the user profile properties.
 */
public record CollectCommand(UserProfile userProfile) implements Command {

    /**
     * Creates a collect command with the specified user profile properties.
     *
     * @param userId          The user ID.
     * @param profileProperty The map of profile properties to collect values.
     */
    public CollectCommand(UserId userId, Map<UserProfilePropertyName, UserProfilePropertyValue> profileProperty) {
        this(new UserProfile(userId, OffsetDateTime.now().toInstant(), profileProperty));
    }


    /**
     * Retrieves the user profile to collect values.
     *
     * @return The user profile.
     */
    public UserProfile getUserProfile() {
        return userProfile;
    }

    @Override
    public CommandType getType() {
        return CommandType.COLLECT;
    }

    @Override
    public UserId getUserId() {
        return getUserProfile().userId();
    }

}

package com.spotlight.platform.userprofile.api.web.commands;

import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import java.time.OffsetDateTime;
import java.util.List;
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

    @Override
    public UserProfile execute(UserProfile oldUserProfile) {

        Map<UserProfilePropertyName, UserProfilePropertyValue> profileProperties = this.getUserProfile().userProfileProperties();
        for (Map.Entry<UserProfilePropertyName, UserProfilePropertyValue> entry : profileProperties.entrySet()) {
            UserProfilePropertyName propertyName = entry.getKey();
            List<String> valuesToAdd = (List<String>) entry.getValue().getValue();
            oldUserProfile = oldUserProfile.collectUserProfileProperty(propertyName, valuesToAdd);
        }
        return oldUserProfile;
    }
}

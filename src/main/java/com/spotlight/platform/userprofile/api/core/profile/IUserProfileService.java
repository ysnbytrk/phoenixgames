package com.spotlight.platform.userprofile.api.core.profile;

import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;

/**
 * Interface for the UserProfileService, responsible for retrieving and updating user profiles.
 */
public interface IUserProfileService {

    /**
     * Retrieves the user profile associated with the given user ID.
     *
     * @param userId The ID of the user.
     * @return The user profile associated with the user ID.
     */
    UserProfile get(UserId userId);

    /**
     * Updates the user profile with the provided profile data.
     *
     * @param userProfile The updated user profile.
     */
    void update(UserProfile userProfile);
}

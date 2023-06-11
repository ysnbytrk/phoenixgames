package com.spotlight.platform.userprofile.api.core.profile;

import com.spotlight.platform.userprofile.api.core.exceptions.EntityNotFoundException;
import com.spotlight.platform.userprofile.api.core.profile.persistence.UserProfileDao;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;

import javax.inject.Inject;

/**
 * Service class for managing user profiles.
 * Provides methods for retrieving and updating user profiles.
 */
public class UserProfileService {
    private final UserProfileDao userProfileDao;

    /**
     * Constructs a new UserProfileService with the provided UserProfileDao.
     *
     * @param userProfileDao The UserProfileDao implementation to use for data access.
     */
    @Inject
    public UserProfileService(UserProfileDao userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

    /**
     * Retrieves the user profile associated with the given user ID.
     *
     * @param userId The ID of the user.
     * @return The user profile associated with the user ID.
     * @throws EntityNotFoundException If the user profile is not found.
     */
    public UserProfile get(UserId userId) {
        return userProfileDao.get(userId).orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Updates the user profile in the data storage.
     *
     * @param userProfile The user profile to update.
     */
    public void update(UserProfile userProfile) {
        userProfileDao.put(userProfile);
    }
}

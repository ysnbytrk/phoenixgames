package com.spotlight.platform.userprofile.api.core.profile;

import com.spotlight.platform.userprofile.api.core.exceptions.EntityNotFoundException;
import com.spotlight.platform.userprofile.api.core.profile.persistence.IUserProfileDao;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the IUserProfileService interface that handles retrieval and update of user profiles.
 */
@Service
public class UserProfileService implements IUserProfileService {
    private final IUserProfileDao userProfileDao;

    @Autowired
    public UserProfileService(IUserProfileDao userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

    /**
     * Retrieves the user profile associated with the given user ID.
     *
     * @param userId The ID of the user.
     * @return The user profile associated with the user ID.
     * @throws EntityNotFoundException If the user profile is not found.
     */
    @Override
    public UserProfile get(UserId userId) {
        return userProfileDao.get(userId).orElseThrow(() -> new EntityNotFoundException(userId));
    }

    /**
     * Updates the user profile with the provided profile data.
     *
     * @param userProfile The updated user profile.
     */
    @Override
    public void update(UserProfile userProfile) {
        userProfileDao.put(userProfile);
    }
}

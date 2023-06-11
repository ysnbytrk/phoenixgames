package com.spotlight.platform.userprofile.api.core.profile.persistence;

import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of the UserProfileDao interface using a ConcurrentHashMap for storage.
 */
@Component
public class UserProfileDaoInMemory implements IUserProfileDao {

    // Storage for user profiles
    private final Map<UserId, UserProfile> storage = new ConcurrentHashMap<>();

    /**
     * Retrieves the user profile associated with the given user ID.
     *
     * @param userId The ID of the user.
     * @return An Optional containing the user profile if found, or an empty Optional if not found.
     */
    @Override
    public Optional<UserProfile> get(UserId userId) {
        return Optional.ofNullable(storage.get(userId));
    }

    /**
     * Stores or updates the user profile in the in-memory storage.
     *
     * @param userProfile The user profile to be stored or updated.
     */
    @Override
    public void put(UserProfile userProfile) {
        storage.put(userProfile.userId(), userProfile);
    }

}

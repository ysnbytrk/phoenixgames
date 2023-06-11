package com.spotlight.platform.userprofile.api.core.profile.persistence;

import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of the {@link UserProfileDao} interface.
 * Stores user profiles in a ConcurrentHashMap for fast retrieval and modification.
 */
public class UserProfileDaoInMemory implements UserProfileDao {
    private final Map<UserId, UserProfile> storage = new ConcurrentHashMap<>();

    /**
     * Retrieves the user profile associated with the given user ID from the in-memory storage.
     *
     * @param userId The ID of the user.
     * @return An {@link Optional} containing the user profile if found, or an empty {@link Optional} if not found.
     */
    @Override
    public Optional<UserProfile> get(UserId userId) {
        return Optional.ofNullable(storage.get(userId));
    }

    /**
     * Stores or updates the user profile in the in-memory storage.
     *
     * @param userProfile The user profile to store or update.
     */
    @Override
    public void put(UserProfile userProfile) {
        storage.put(userProfile.userId(), userProfile);
    }
}


package com.spotlight.platform.userprofile.api.core.profile.persistence;

import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;

import java.util.Optional;

import java.util.Optional;

/**
 * Interface for accessing and modifying user profiles in the data store.
 */
public interface UserProfileDao {
    /**
     * Retrieves the user profile associated with the given user ID.
     *
     * @param userId The ID of the user.
     * @return An {@link Optional} containing the user profile if found, or an empty {@link Optional} if not found.
     */
    Optional<UserProfile> get(UserId userId);

    /**
     * Stores or updates the user profile in the data store.
     *
     * @param userProfile The user profile to store or update.
     */
    void put(UserProfile userProfile);
}

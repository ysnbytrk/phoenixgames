package com.spotlight.platform.userprofile.api.core.profile.persistence;

import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;

import java.util.Optional;

/**
 * Data Access Object (DAO) for accessing and manipulating user profiles in the persistence layer.
 */
public interface IUserProfileDao {

    /**
     * Retrieves the user profile associated with the given user ID.
     *
     * @param userId The ID of the user.
     * @return An Optional containing the user profile if found, or an empty Optional if not found.
     */
    Optional<UserProfile> get(UserId userId);

    /**
     * Stores or updates the user profile in the persistence layer.
     *
     * @param userProfile The user profile to be stored or updated.
     */
    void put(UserProfile userProfile);

}

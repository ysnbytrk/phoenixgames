package com.spotlight.platform.userprofile.api.core.exceptions;

import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;

/**
 * Exception thrown when a user profile entity is not found.
 */
public class EntityNotFoundException extends RuntimeException {


    /**
     * Constructs an EntityNotFoundException with the specified UserId.
     *
     * @param userId The UserId for which the user profile is not found.
     */
    public EntityNotFoundException(UserId userId) {
        super("UserProfile not found with UserId::" + userId);
    }
}

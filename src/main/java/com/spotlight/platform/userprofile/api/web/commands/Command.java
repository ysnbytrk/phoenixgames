package com.spotlight.platform.userprofile.api.web.commands;


import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;

/**
 * Represents a command to be executed on the user profile.
 */
public interface Command {
    /**
     * Returns the type of the command.
     *
     * @return The command type.
     */
    CommandType getType();

    /**
     * Returns the UserId of the command
     *
     * @return the UserId type
     */
    UserId getUserId();

    /**
     * Executes the command on the provided user profile, producing a new or modified user profile.
     *
     * @param oldUserProfile The original user profile to be modified by the command.
     * @return The resulting user profile after executing the command.
     */
    UserProfile execute(UserProfile oldUserProfile);

}

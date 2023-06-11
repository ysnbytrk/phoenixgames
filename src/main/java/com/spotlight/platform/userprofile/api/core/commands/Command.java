package com.spotlight.platform.userprofile.api.core.commands;

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
     * Returns the UserId of the command.
     *
     * @return The UserId of the command.
     */
    UserId getUserId();

}

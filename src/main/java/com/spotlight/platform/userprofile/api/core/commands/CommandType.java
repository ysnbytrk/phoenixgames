package com.spotlight.platform.userprofile.api.core.commands;

/**
 * Represents the type of a command to be executed on the user profile.
 */
public enum CommandType {
    /**
     * The REPLACE command type is used to replace the entire user profile with a new profile.
     */
    REPLACE,

    /**
     * The INCREMENT command type is used to increment the values of specific properties in the user profile.
     */
    INCREMENT,

    UNSUPPORTED,
    /**
     * The COLLECT command type is used to collect values into a list within the user profile properties.
     */
    COLLECT
}

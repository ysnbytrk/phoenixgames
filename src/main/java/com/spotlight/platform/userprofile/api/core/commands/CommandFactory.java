package com.spotlight.platform.userprofile.api.core.commands;

import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import java.util.Map;

/**
 * Factory class to create specific command instances based on the command type.
 */
public class CommandFactory {

    /**
     * Creates a command based on the command type.
     *
     * @param commandData The data of the command.
     * @return An instance of the appropriate command.
     * @throws IllegalArgumentException if the command type is not supported.
     */
    public static Command createCommand(CommandData commandData) {
        CommandType type = commandData.type();

        // Use a switch statement to determine the command type and create the appropriate command instance
        return switch (type) {
            case REPLACE -> createReplaceCommand(commandData);
            case INCREMENT -> createIncrementCommand(commandData);
            case COLLECT -> createCollectCommand(commandData);
            default -> throw new IllegalArgumentException("Unsupported command type: " + type);
        };
    }

    /**
     * Creates a ReplaceCommand instance based on the command data.
     *
     * @param commandData The data of the command.
     * @return A ReplaceCommand instance.
     */
    private static ReplaceCommand createReplaceCommand(CommandData commandData) {
        // Create a new UserProfile instance with the provided user ID, null latestUpdateTime, and properties
        UserProfile userProfile = new UserProfile(commandData.userId(), null, commandData.properties());
        return new ReplaceCommand(userProfile);
    }

    /**
     * Creates an IncrementCommand instance based on the command data.
     *
     * @param commandData The data of the command.
     * @return An IncrementCommand instance.
     */
    private static IncrementCommand createIncrementCommand(CommandData commandData) {
        // Get the properties from the command data
        Map<UserProfilePropertyName, UserProfilePropertyValue> properties = commandData.properties();
        return new IncrementCommand(commandData.userId(), properties);
    }

    /**
     * Creates a CollectCommand instance based on the command data.
     *
     * @param commandData The data of the command.
     * @return A CollectCommand instance.
     */
    private static CollectCommand createCollectCommand(CommandData commandData) {
        // Get the properties from the command data
        Map<UserProfilePropertyName, UserProfilePropertyValue> properties = commandData.properties();
        return new CollectCommand(commandData.userId(), properties);
    }
}

package com.spotlight.platform.userprofile.api.web.commands;

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
        return switch (type) {
            case REPLACE -> createReplaceCommand(commandData);
            case INCREMENT -> createIncrementCommand(commandData);
            case COLLECT -> createCollectCommand(commandData);
            default -> throw new IllegalArgumentException("Unsupported command type: " + type);
        };
    }

    private static ReplaceCommand createReplaceCommand(CommandData commandData) {
        UserProfile userProfile = new UserProfile(commandData.userId(), null, commandData.properties());
        return new ReplaceCommand(userProfile);
    }

    private static IncrementCommand createIncrementCommand(CommandData commandData) {
        Map<UserProfilePropertyName, UserProfilePropertyValue> properties = commandData.properties();
        return new IncrementCommand(commandData.userId(), properties);
    }

    private static CollectCommand createCollectCommand(CommandData commandData) {
        Map<UserProfilePropertyName, UserProfilePropertyValue> properties = commandData.properties();
        return new CollectCommand(commandData.userId(), properties);
    }
}

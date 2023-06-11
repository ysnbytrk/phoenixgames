package com.spotlight.platform.userprofile.api.core.command.executer;

import com.spotlight.platform.userprofile.api.core.commands.Command;
import com.spotlight.platform.userprofile.api.core.commands.CommandType;
import com.spotlight.platform.userprofile.api.core.commands.executer.CommandExecutor;
import com.spotlight.platform.userprofile.api.core.commands.executer.CommandExecutorRegistry;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandExecutorRegistryTest {

    @Test
    @DisplayName("Execute command with registered CommandExecutor")
    void executeCommandWithRegisteredExecutor() {
        // Create a stub implementation of CommandExecutor
        CommandExecutor mockExecutor = command -> {
            // Do nothing or perform custom behavior for testing
        };

        // Create a CommandExecutorRegistry
        CommandExecutorRegistry executorRegistry = new CommandExecutorRegistry();

        // Register the stub executor for the command type
        executorRegistry.registerExecutor(CommandType.REPLACE, mockExecutor);

        // Create a command with a registered command type
        Command command = new Command() {
            @Override
            public CommandType getType() {
                return CommandType.REPLACE;
            }

            @Override
            public UserId getUserId() {
                return null;
            }
        };

        // Execute the command
        executorRegistry.execute(command);

    }

    @Test
    @DisplayName("Execute command with unsupported command type")
    void executeCommandWithUnsupportedType() {
        // Create an empty CommandExecutorRegistry
        CommandExecutorRegistry executorRegistry = new CommandExecutorRegistry();

        // Create a command with an unsupported command type
        Command command = new Command() {
            @Override
            public CommandType getType() {
                return CommandType.COLLECT;
            }

            @Override
            public UserId getUserId() {
                return null;
            }
        };

        // Execute the command and expect an IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> executorRegistry.execute(command));

        // Verify the exception message
        assertEquals("Unsupported command type: " + CommandType.COLLECT, exception.getMessage());
    }
}

package com.spotlight.platform.userprofile.api.core.commands.executer;

import com.spotlight.platform.userprofile.api.core.commands.Command;
import com.spotlight.platform.userprofile.api.core.commands.CommandType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry for command executors.
 */
public class CommandExecutorRegistry {

    private final Map<CommandType, CommandExecutor> executorMap;

    @Autowired
    public CommandExecutorRegistry() {
        this.executorMap = new HashMap<>();
    }

    /**
     * Registers a command executor for the specified command type.
     *
     * @param commandType The command type.
     * @param executor    The command executor.
     */
    public void registerExecutor(CommandType commandType, CommandExecutor executor) {
        executorMap.put(commandType, executor);
    }

    /**
     * Executes the given command by retrieving the appropriate executor based on the command type.
     *
     * @param command The command to execute.
     * @throws IllegalArgumentException if the command type is not supported.
     */
    public void execute(Command command) {
        CommandExecutor executor = this.getExecutor(command.getType());
        if (executor == null) {
            throw new IllegalArgumentException("Unsupported command type: " + command.getType());
        }
        executor.execute(command);
    }

    /**
     * Retrieves the command executor for the specified command type.
     *
     * @param commandType The command type.
     * @return The command executor, or null if no executor is registered for the given command type.
     */
    public CommandExecutor getExecutor(CommandType commandType) {
        return executorMap.get(commandType);
    }
}

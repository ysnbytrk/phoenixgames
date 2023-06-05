package com.spotlight.platform.userprofile.api.core.commands.executer;

import com.spotlight.platform.userprofile.api.core.commands.Command;
import com.spotlight.platform.userprofile.api.core.commands.CommandType;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class CommandExecutorRegistry {
    private final Map<CommandType, CommandExecutor> executorMap;

    @Inject
    public CommandExecutorRegistry() {
        this.executorMap = new HashMap<>();
    }

    public void registerExecutor(CommandType commandType, CommandExecutor executor) {
        executorMap.put(commandType, executor);
    }

    public void execute(Command command) {
        CommandExecutor executor = this.getExecutor(command.getType());
        if (executor == null) {
            throw new IllegalArgumentException("Unsupported command type: " + command.getType());
        }
        executor.execute(command);
    }

    public CommandExecutor getExecutor(CommandType commandType) {
        return executorMap.get(commandType);
    }
}

package com.spotlight.platform.userprofile.api.core.commands.executer;

import com.spotlight.platform.userprofile.api.core.commands.Command;
import com.spotlight.platform.userprofile.api.core.commands.CommandType;

import javax.inject.Inject;
import java.util.Map;

public class CommandExecutorRegistry {
    private final Map<CommandType, CommandExecutor> executorMap;

    @Inject
    public CommandExecutorRegistry(Map<CommandType, CommandExecutor> executorMap) {
        this.executorMap = executorMap;
    }

    public void execute(Command command) {
        CommandExecutor executor = executorMap.get(command.getType());
        if (executor == null) {
            throw new IllegalArgumentException("Unsupported command type: " + command.getType());
        }
        executor.execute(command);
    }
}

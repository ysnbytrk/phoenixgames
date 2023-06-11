package com.spotlight.platform.userprofile.api.core.commands.executer;

import com.spotlight.platform.userprofile.api.core.commands.Command;

/**
 * Interface for executing commands.
 */
public interface CommandExecutor {

    /**
     * Executes the given command.
     *
     * @param command The command to execute.
     */
    void execute(Command command);
}

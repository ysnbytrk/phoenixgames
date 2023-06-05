package com.spotlight.platform.userprofile.api.core.commands.executer;

import com.spotlight.platform.userprofile.api.core.commands.Command;

public interface CommandExecutor {
    void execute(Command command);
}

package com.spotlight.platform.userprofile.api.web.controller;

import com.spotlight.platform.userprofile.api.core.commands.Command;
import com.spotlight.platform.userprofile.api.core.commands.CommandData;
import com.spotlight.platform.userprofile.api.core.commands.CommandFactory;
import com.spotlight.platform.userprofile.api.core.commands.executer.CommandExecutorRegistry;
import com.spotlight.platform.userprofile.api.core.exceptions.InvalidCommandException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/commands")
@Api(tags = "User Profile Commands")
public class CommandController {

    private final CommandExecutorRegistry executorRegistry;

    @Autowired
    public CommandController(CommandExecutorRegistry executorRegistry) {
        this.executorRegistry = executorRegistry;
    }

    @PostMapping
    @ApiOperation(value = "Handle commands", notes = "Handles a list of command data by creating and executing the corresponding commands.")
    public ResponseEntity<String> handleCommands(@RequestBody List<CommandData> commandDataList) {
        for (CommandData commandData : commandDataList) {
            Command command = CommandFactory.createCommand(commandData);
            if (command == null) {
                // Handle unsupported command type
                throw new InvalidCommandException("Invalid command type");
            }
            executorRegistry.execute(command);
        }

        // Return a success response
        return ResponseEntity.ok().build();
    }
}

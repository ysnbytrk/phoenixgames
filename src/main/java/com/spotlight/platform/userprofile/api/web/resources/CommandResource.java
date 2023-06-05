package com.spotlight.platform.userprofile.api.web.resources;


import com.spotlight.platform.userprofile.api.core.commands.Command;
import com.spotlight.platform.userprofile.api.core.commands.CommandData;
import com.spotlight.platform.userprofile.api.core.commands.CommandFactory;
import com.spotlight.platform.userprofile.api.core.commands.executer.CommandExecutorRegistry;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/commands")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommandResource {

    private final CommandExecutorRegistry executorRegistry;

    @Inject
    public CommandResource(CommandExecutorRegistry executorRegistry) {
        this.executorRegistry = executorRegistry;
    }

    @POST
    public Response handleCommands(List<CommandData> commandDataList) {
        for (CommandData commandData : commandDataList) {
            try {
                Command command = CommandFactory.createCommand(commandData);
                if (command == null) {
                    // Handle unsupported command type
                    return Response.status(Response.Status.BAD_REQUEST).entity("Invalid command type").build();
                }

                executorRegistry.execute(command);

            } catch (IllegalArgumentException e) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid command type").build();
            }
        }


        // Return a success response
        return Response.ok().build();
    }
}

package com.spotlight.platform.userprofile.api.web.resources;

import com.spotlight.platform.userprofile.api.core.commands.executer.CommandExecutorRegistry;
import com.spotlight.platform.userprofile.api.core.profile.UserProfileService;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.core.commands.Command;
import com.spotlight.platform.userprofile.api.core.commands.CommandData;
import com.spotlight.platform.userprofile.api.core.commands.CommandFactory;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users/{userId}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserProfileService userProfileService;
    private final CommandExecutorRegistry executorRegistry;

    @Inject
    public UserResource(UserProfileService userProfileService, CommandExecutorRegistry executorRegistry) {
        this.userProfileService = userProfileService;
        this.executorRegistry = executorRegistry;
    }

    @Path("profile")
    @GET
    public UserProfile getUserProfile(@Valid @PathParam("userId") UserId userId) {
        return userProfileService.get(userId);
    }

    @GET
    public UserProfile getUserProfile() {
        return userProfileService.get(UserId.valueOf("0"));
    }

    @Path("/commands")
    @POST
    public Response handleCommands(List<CommandData> commandDataList) {
        for (CommandData commandData : commandDataList) {
            Command command = CommandFactory.createCommand(commandData);
            if (command == null) {
                // Handle unsupported command type
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid command type").build();
            }

            executorRegistry.execute(command);
        }

        // Return a success response
        return Response.ok().build();
    }


}

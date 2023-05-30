package com.spotlight.platform.userprofile.api.web.resources;

import com.spotlight.platform.userprofile.api.core.profile.UserProfileService;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.web.commands.Command;
import com.spotlight.platform.userprofile.api.web.commands.CommandData;
import com.spotlight.platform.userprofile.api.web.commands.CommandFactory;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/users/{userId}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserProfileService userProfileService;

    @Inject
    public UserResource(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
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
        List<Command> commands = new ArrayList<>();

        for (CommandData commandData : commandDataList) {
            Command command = CommandFactory.createCommand(commandData);
            if (command == null) {
                // Handle unsupported command type
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid command type").build();
            }
            commands.add(command);
        }

        for (Command command : commands) {
            // Process each command
            userProfileService.executeCommand(command);
        }

        // Return a success response
        return Response.ok().build();
    }


}

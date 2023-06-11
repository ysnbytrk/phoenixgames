package com.spotlight.platform.userprofile.api.web.resources;

import com.spotlight.platform.userprofile.api.core.profile.UserProfileService;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/users/{userId}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
/**
 * Resource class for managing user profiles.
 */
public class UserResource {

    private final UserProfileService userProfileService;

    @Inject
    public UserResource(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    /**
     * Retrieves the user profile for the specified user ID.
     *
     * @param userId The user ID path parameter.
     * @return The user profile.
     */
    @Path("profile")
    @GET
    public UserProfile getUserProfile(@Valid @PathParam("userId") UserId userId) {
        return userProfileService.get(userId);
    }

    /**
     * Retrieves the user profile for the default user ID.
     * This method is used as a fallback when the user ID is not provided in the path parameter.
     *
     * @return The user profile.
     */
    @GET
    public UserProfile getUserProfile() {
        return userProfileService.get(UserId.valueOf("0"));
    }
}

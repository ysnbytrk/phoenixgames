package com.spotlight.platform.userprofile.api.web.controller;

import com.spotlight.platform.userprofile.api.core.profile.IUserProfileService;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling user profile-related endpoints.
 */
@RestController
@RequestMapping("/users/{userId}")
@Api(tags = "User Profiles")
public class UserProfileController {

    private final IUserProfileService userProfileService;

    @Autowired
    public UserProfileController(IUserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    /**
     * Retrieves the user profile for the specified user ID.
     *
     * @param userId The user ID.
     * @return The user profile.
     */
    @GetMapping("/profile")
    @ApiOperation(value = "Get User Profile", notes = "Retrieves the user profile for the specified user ID.")
    public UserProfile getUserProfile(@PathVariable("userId") UserId userId) {
        return userProfileService.get(userId);
    }

    /**
     * Retrieves the user profile for a default user ID.
     *
     * @return The user profile.
     */
    @GetMapping
    @ApiOperation(value = "Get Default User Profile", notes = "Retrieves the user profile for a default user ID.")
    public UserProfile getUserProfile() {
        return userProfileService.get(UserId.valueOf("0"));
    }
}

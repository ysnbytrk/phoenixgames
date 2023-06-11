package com.spotlight.platform.userprofile.api.core.commands.executer;

import com.spotlight.platform.userprofile.api.core.commands.Command;
import com.spotlight.platform.userprofile.api.core.commands.IncrementCommand;
import com.spotlight.platform.userprofile.api.core.profile.UserProfileService;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Executor for the IncrementCommand.
 */
@Component
@Qualifier("IncrementCommandExecutor")
public class IncrementCommandExecutor implements CommandExecutor {

    private final UserProfileService userProfileService;

    @Autowired
    public IncrementCommandExecutor(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    /**
     * Executes the IncrementCommand by incrementing the specified properties in the user profile.
     *
     * @param command The IncrementCommand to execute.
     */
    @Override
    public void execute(Command command) {
        IncrementCommand incrementCommand = (IncrementCommand) command;
        UserProfile oldUserProfile = userProfileService.get(incrementCommand.getUserId());
        Map<UserProfilePropertyName, UserProfilePropertyValue> increments = incrementCommand.getUserProfile().userProfileProperties();

        for (Map.Entry<UserProfilePropertyName, UserProfilePropertyValue> entry : increments.entrySet()) {
            UserProfilePropertyName propertyName = entry.getKey();
            UserProfilePropertyValue userProfilePropertyValue = entry.getValue();
            int incrementValue = Integer.parseInt(userProfilePropertyValue.getValue().toString());
            oldUserProfile = oldUserProfile.incrementUserProfileProperty(propertyName, incrementValue);
        }

        userProfileService.update(oldUserProfile);
    }
}

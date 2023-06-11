package com.spotlight.platform.userprofile.api.core.commands.executer;

import com.spotlight.platform.userprofile.api.core.commands.Command;
import com.spotlight.platform.userprofile.api.core.commands.ReplaceCommand;
import com.spotlight.platform.userprofile.api.core.exceptions.EntityNotFoundException;
import com.spotlight.platform.userprofile.api.core.profile.IUserProfileService;
import com.spotlight.platform.userprofile.api.core.profile.UserProfileService;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Executor for the ReplaceCommand.
 */
@Component
@Qualifier("ReplaceCommandExecutor")
public class ReplaceCommandExecutor implements CommandExecutor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final IUserProfileService userProfileService;

    @Autowired
    public ReplaceCommandExecutor(IUserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    /**
     * Executes the ReplaceCommand by replacing the specified properties in the user profile.
     *
     * @param command The ReplaceCommand to execute.
     * @throws EntityNotFoundException if the user profile is not found.
     */
    @Override
    public void execute(Command command) throws EntityNotFoundException {
        ReplaceCommand replaceCommand = (ReplaceCommand) command;
        UserProfile oldUserProfile = userProfileService.get(replaceCommand.getUserId());
        Map<UserProfilePropertyName, UserProfilePropertyValue> properties = replaceCommand.getUserProfile().userProfileProperties();

        for (Map.Entry<UserProfilePropertyName, UserProfilePropertyValue> entry : properties.entrySet()) {
            UserProfilePropertyName propertyName = entry.getKey();
            UserProfilePropertyValue propertyValue = entry.getValue();
            oldUserProfile = oldUserProfile.withUserProfileProperty(propertyName, propertyValue);
        }

        userProfileService.update(oldUserProfile);
    }
}

package com.spotlight.platform.userprofile.api.core.commands.executer;

import com.google.inject.Inject;
import com.spotlight.platform.userprofile.api.core.commands.Command;
import com.spotlight.platform.userprofile.api.core.commands.CommandType;
import com.spotlight.platform.userprofile.api.core.commands.ReplaceCommand;
import com.spotlight.platform.userprofile.api.core.commands.executer.annotation.ExecuterQualifier;
import com.spotlight.platform.userprofile.api.core.exceptions.EntityNotFoundException;
import com.spotlight.platform.userprofile.api.core.profile.UserProfileService;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Map;

/**
 * Executor for the ReplaceCommand.
 */
@Singleton
@ExecuterQualifier(CommandType.REPLACE)
public class ReplaceCommandExecutor implements CommandExecutor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserProfileService userProfileService;

    @Inject
    public ReplaceCommandExecutor(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    /**
     * Executes the ReplaceCommand by replacing the corresponding user profile properties.
     *
     * @param command The ReplaceCommand to execute.
     * @throws EntityNotFoundException if the user profile is not found.
     */
    @Override
    public void execute(Command command) {
        ReplaceCommand replaceCommand = (ReplaceCommand) command;
        try {
            UserProfile oldUserProfile = userProfileService.get(replaceCommand.getUserId());
            Map<UserProfilePropertyName, UserProfilePropertyValue> properties = replaceCommand.getUserProfile().userProfileProperties();
            for (Map.Entry<UserProfilePropertyName, UserProfilePropertyValue> entry : properties.entrySet()) {
                UserProfilePropertyName propertyName = entry.getKey();
                UserProfilePropertyValue propertyValue = entry.getValue();
                oldUserProfile = oldUserProfile.withUserProfileProperty(propertyName, propertyValue);
            }
            userProfileService.update(oldUserProfile);
        } catch (EntityNotFoundException e) {
            logger.error("UserProfile not found with UserId: " + replaceCommand.getUserId(), e);
            throw e;
        }
    }

    /**
     * Sets the logger to use for logging.
     *
     * @param logger The logger to set.
     */
    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}

package com.spotlight.platform.userprofile.api.core.commands.executer;

import com.spotlight.platform.userprofile.api.core.commands.Command;
import com.spotlight.platform.userprofile.api.core.commands.IncrementCommand;
import com.spotlight.platform.userprofile.api.core.exceptions.EntityNotFoundException;
import com.spotlight.platform.userprofile.api.core.profile.UserProfileService;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Map;

public class IncrementCommandExecutor implements CommandExecutor {
    private final UserProfileService userProfileService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    public IncrementCommandExecutor(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @Override
    public void execute(Command command) {
        IncrementCommand incrementCommand = (IncrementCommand) command;
        try {
            UserProfile oldUserProfile = userProfileService.get(incrementCommand.getUserId());
            Map<UserProfilePropertyName, UserProfilePropertyValue> increments = incrementCommand.getUserProfile().userProfileProperties();
            for (Map.Entry<UserProfilePropertyName, UserProfilePropertyValue> entry : increments.entrySet()) {
                UserProfilePropertyName propertyName = entry.getKey();
                final UserProfilePropertyValue userProfilePropertyValue = entry.getValue();
                int incrementValue = Integer.parseInt(userProfilePropertyValue.getValue().toString());
                oldUserProfile = oldUserProfile.incrementUserProfileProperty(propertyName, incrementValue);
            }
            userProfileService.update(oldUserProfile);
        } catch (EntityNotFoundException e) {
            logger.error("UserProfile not found with UserId::" + incrementCommand.getUserId(), e);
            throw e;
        }
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}

package com.spotlight.platform.userprofile.api.core.commands.executer;

import com.spotlight.platform.userprofile.api.core.commands.CollectCommand;
import com.spotlight.platform.userprofile.api.core.commands.Command;
import com.spotlight.platform.userprofile.api.core.exceptions.EntityNotFoundException;
import com.spotlight.platform.userprofile.api.core.profile.UserProfileService;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectCommandExecutor implements CommandExecutor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserProfileService userProfileService;

    @Inject
    public CollectCommandExecutor(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @Override
    public void execute(Command command) {
        CollectCommand collectCommand = (CollectCommand) command;
        try {
            UserProfile oldUserProfile = userProfileService.get(collectCommand.getUserId());

            UserProfile updatedUserProfile = collectProperties(oldUserProfile, collectCommand.getUserProfile().userProfileProperties());
            userProfileService.update(updatedUserProfile);
        } catch (EntityNotFoundException e) {
            logger.error("UserProfile not found with UserId::" + collectCommand.getUserId(), e);
        }
    }

    private UserProfile collectProperties(UserProfile oldUserProfile, Map<UserProfilePropertyName, UserProfilePropertyValue> propertiesToAdd) {
        Map<UserProfilePropertyName, UserProfilePropertyValue> updatedProperties = new HashMap<>(oldUserProfile.userProfileProperties());

        for (Map.Entry<UserProfilePropertyName, UserProfilePropertyValue> entry : propertiesToAdd.entrySet()) {
            UserProfilePropertyName propertyName = entry.getKey();
            UserProfilePropertyValue propertyValue = entry.getValue();

            List<String> valuesToAdd = new ArrayList<>();
            Object value = propertyValue.getValue();

            if (value instanceof List<?>) {
                ((List<?>) value).forEach(o -> valuesToAdd.add(o.toString()));
            } else {
                valuesToAdd.add(value.toString());
            }

            List<String> existingValues = extractValues(oldUserProfile, propertyName);
            existingValues.addAll(valuesToAdd);

            updatedProperties.put(propertyName, UserProfilePropertyValue.valueOf(existingValues));
        }

        return new UserProfile(oldUserProfile.userId(), oldUserProfile.latestUpdateTime(), updatedProperties);
    }

    private List<String> extractValues(UserProfile userProfile, UserProfilePropertyName propertyName) {
        UserProfilePropertyValue propertyValue = userProfile.userProfileProperties().get(propertyName);

        if (propertyValue == null) {
            return new ArrayList<>();
        }

        Object value = propertyValue.getValue();
        List<String> values = new ArrayList<>();
        if (value instanceof List<?>) {
            ((List<?>) value).forEach(o -> values.add(o.toString()));
            return values;
        } else {
            throw new IllegalArgumentException("Invalid property value type. Expected List.");
        }
    }
}

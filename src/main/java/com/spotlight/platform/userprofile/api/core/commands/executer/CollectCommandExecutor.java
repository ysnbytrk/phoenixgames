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

/**
 * Command executor implementation for executing a CollectCommand.
 */
public class CollectCommandExecutor implements CommandExecutor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserProfileService userProfileService;

    @Inject
    public CollectCommandExecutor(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    /**
     * Executes the CollectCommand by collecting and updating the user profile properties.
     *
     * @param command The CollectCommand to execute.
     * @throws EntityNotFoundException if the UserProfile is not found.
     */
    @Override
    public void execute(Command command) {
        CollectCommand collectCommand = (CollectCommand) command;
        try {
            UserProfile oldUserProfile = userProfileService.get(collectCommand.getUserId());

            UserProfile updatedUserProfile = collectProperties(oldUserProfile, collectCommand.getUserProfile().userProfileProperties());
            userProfileService.update(updatedUserProfile);
        } catch (EntityNotFoundException e) {
            logger.error("UserProfile not found with UserId: " + collectCommand.getUserId(), e);
            throw e;
        }
    }

    /**
     * Collects the properties to be added to the user profile and returns the updated user profile.
     *
     * @param oldUserProfile   The existing user profile.
     * @param propertiesToAdd  The properties to be added.
     * @return The updated user profile.
     */
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

    /**
     * Extracts the values of a property from the user profile.
     *
     * @param userProfile    The user profile.
     * @param propertyName   The name of the property.
     * @return The values of the property.
     * @throws IllegalArgumentException if the property value is not of type List.
     */
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

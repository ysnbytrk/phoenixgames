package com.spotlight.platform.userprofile.api.core.commands.executer;

import com.spotlight.platform.userprofile.api.core.commands.CollectCommand;
import com.spotlight.platform.userprofile.api.core.commands.Command;
import com.spotlight.platform.userprofile.api.core.profile.UserProfileService;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Executor for the {@link CollectCommand}.
 * Collects properties from the command and updates the user profile.
 */
@Component
@Qualifier("CollectCommandExecutor")
public class CollectCommandExecutor implements CommandExecutor {
    private final UserProfileService userProfileService;

    @Autowired
    public CollectCommandExecutor(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    /**
     * Executes the collect command.
     *
     * @param command The collect command to execute.
     */
    @Override
    public void execute(Command command) {
        CollectCommand collectCommand = (CollectCommand) command;

        // Get the old user profile
        UserProfile oldUserProfile = userProfileService.get(collectCommand.getUserId());

        // Collect properties and update the user profile
        UserProfile updatedUserProfile = collectProperties(oldUserProfile, collectCommand.getUserProfile().userProfileProperties());
        userProfileService.update(updatedUserProfile);
    }

    /**
     * Collects properties to be added to the user profile.
     *
     * @param oldUserProfile   The old user profile.
     * @param propertiesToAdd  The properties to be added.
     * @return The updated user profile with collected properties.
     */
    private UserProfile collectProperties(UserProfile oldUserProfile, Map<UserProfilePropertyName, UserProfilePropertyValue> propertiesToAdd) {
        Map<UserProfilePropertyName, UserProfilePropertyValue> updatedProperties = new HashMap<>(oldUserProfile.userProfileProperties());

        for (Map.Entry<UserProfilePropertyName, UserProfilePropertyValue> entry : propertiesToAdd.entrySet()) {
            UserProfilePropertyName propertyName = entry.getKey();
            UserProfilePropertyValue propertyValue = entry.getValue();

            List<String> valuesToAdd = new ArrayList<>();
            Object value = propertyValue.getValue();

            // If the value is a list, convert each element to a string
            if (value instanceof List<?>) {
                ((List<?>) value).forEach(o -> valuesToAdd.add(o.toString()));
            } else {
                valuesToAdd.add(value.toString());
            }

            // Get existing values for the property and add the new values
            List<String> existingValues = extractValues(oldUserProfile, propertyName);
            existingValues.addAll(valuesToAdd);

            updatedProperties.put(propertyName, UserProfilePropertyValue.valueOf(existingValues));
        }

        return new UserProfile(oldUserProfile.userId(), oldUserProfile.latestUpdateTime(), updatedProperties);
    }

    /**
     * Extracts the values for a specific property from the user profile.
     *
     * @param userProfile   The user profile.
     * @param propertyName  The name of the property.
     * @return The list of values for the property.
     * @throws IllegalArgumentException If the property value type is invalid (expected List).
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

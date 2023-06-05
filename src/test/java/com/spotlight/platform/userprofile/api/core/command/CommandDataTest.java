package com.spotlight.platform.userprofile.api.core.command;

import com.spotlight.platform.userprofile.api.core.commands.CommandData;
import com.spotlight.platform.userprofile.api.core.commands.CommandType;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CommandDataTest {

    @Test
    @DisplayName("Create CommandData instance")
    void createCommandDataInstance() {
        // Create sample data for CommandData
        UserId userId = UserId.valueOf("123");
        CommandType type = CommandType.REPLACE;
        Map<UserProfilePropertyName, UserProfilePropertyValue> properties = new HashMap<>();
        properties.put(UserProfilePropertyName.valueOf("name"), UserProfilePropertyValue.valueOf("John"));

        // Create CommandData instance
        CommandData commandData = new CommandData(userId, type, properties);

        // Verify the instance is created successfully
        assertNotNull(commandData);
    }

    @Test
    @DisplayName("Retrieve user ID from CommandData")
    void retrieveUserIdFromCommandData() {
        // Create sample data for CommandData
        UserId userId = UserId.valueOf("123");
        CommandType type = CommandType.REPLACE;
        Map<UserProfilePropertyName, UserProfilePropertyValue> properties = new HashMap<>();

        // Create CommandData instance
        CommandData commandData = new CommandData(userId, type, properties);

        // Retrieve the user ID from CommandData
        UserId retrievedUserId = commandData.userId();

        // Verify the retrieved user ID matches the original user ID
        assertEquals(userId, retrievedUserId);
    }

    @Test
    @DisplayName("Retrieve command type from CommandData")
    void retrieveCommandTypeFromCommandData() {
        // Create sample data for CommandData
        UserId userId = UserId.valueOf("123");
        CommandType type = CommandType.REPLACE;
        Map<UserProfilePropertyName, UserProfilePropertyValue> properties = new HashMap<>();

        // Create CommandData instance
        CommandData commandData = new CommandData(userId, type, properties);

        // Retrieve the command type from CommandData
        CommandType retrievedType = commandData.type();

        // Verify the retrieved command type matches the original command type
        assertEquals(type, retrievedType);
    }

    @Test
    @DisplayName("Retrieve properties from CommandData")
    void retrievePropertiesFromCommandData() {
        // Create sample data for CommandData
        UserId userId = UserId.valueOf("123");
        CommandType type = CommandType.REPLACE;
        Map<UserProfilePropertyName, UserProfilePropertyValue> properties = new HashMap<>();
        properties.put(UserProfilePropertyName.valueOf("name"), UserProfilePropertyValue.valueOf("John"));

        // Create CommandData instance
        CommandData commandData = new CommandData(userId, type, properties);

        // Retrieve the properties from CommandData
        Map<UserProfilePropertyName, UserProfilePropertyValue> retrievedProperties = commandData.properties();

        // Verify the retrieved properties match the original properties
        assertEquals(properties, retrievedProperties);
    }
}

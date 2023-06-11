package com.spotlight.platform.userprofile.api.core.command.executer;

import com.spotlight.platform.userprofile.api.core.commands.CollectCommand;
import com.spotlight.platform.userprofile.api.core.commands.executer.CollectCommandExecutor;
import com.spotlight.platform.userprofile.api.core.profile.UserProfileService;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


class CollectCommandExecutorTest {
    private CollectCommandExecutor executor;

    @Mock
    private UserProfileService userProfileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        executor = new CollectCommandExecutor(userProfileService);
    }

    @Test
    @DisplayName("Execute CollectCommand successfully")
    void executeCollectCommand() {
        // Prepare test data
        UserProfile oldUserProfile = UserProfileFixtures.USER_PROFILE;
        UserProfilePropertyName propertyName = UserProfilePropertyName.valueOf("interests");
        List<String> valuesToAdd = Arrays.asList("music", "movies");
        UserProfilePropertyValue propertyValue = UserProfilePropertyValue.valueOf(valuesToAdd);

        Map<UserProfilePropertyName, UserProfilePropertyValue> profileProperties = new HashMap<>();
        profileProperties.put(propertyName, propertyValue);

        CollectCommand collectCommand = new CollectCommand(UserProfileFixtures.USER_ID, profileProperties);

        // Mock the behavior of UserProfileService
        when(userProfileService.get(UserProfileFixtures.USER_ID)).thenReturn(oldUserProfile);

        // Execute the command
        executor.execute(collectCommand);

        // Verify that UserProfileService was called with the correct arguments
        verify(userProfileService).get(UserProfileFixtures.USER_ID);
        verify(userProfileService).update(any(UserProfile.class));

        // Verify the updated user profile
        UserProfile expectedUserProfile = oldUserProfile.collectUserProfileProperty(propertyName, valuesToAdd);
        verify(userProfileService).update(expectedUserProfile);
    }


    @Test
    public void testExecute_AddNewProperty() {
        // Arrange
        UserProfile existingUserProfile = new UserProfile(
                UserId.valueOf("existing-user-id"),
                Instant.parse("2023-06-01T09:16:36.123Z"),
                new HashMap<>()
        );
        when(userProfileService.get(UserId.valueOf("existing-user-id"))).thenReturn(existingUserProfile);

        Map<UserProfilePropertyName, UserProfilePropertyValue> propertiesToAdd = new HashMap<>();
        UserProfilePropertyName newPropertyName = UserProfilePropertyName.valueOf("new-property");
        UserProfilePropertyValue newPropertyValue = UserProfilePropertyValue.valueOf("new-value");
        propertiesToAdd.put(newPropertyName, newPropertyValue);

        CollectCommand collectCommand = new CollectCommand(
                UserId.valueOf("existing-user-id"),
                propertiesToAdd
        );

        // Act
        executor.execute(collectCommand);

        // Assert
        ArgumentCaptor<UserProfile> userProfileCaptor = ArgumentCaptor.forClass(UserProfile.class);
        verify(userProfileService).update(userProfileCaptor.capture());

        UserProfile updatedUserProfile = userProfileCaptor.getValue();
        assertEquals(UserId.valueOf("existing-user-id"), updatedUserProfile.userId());
        assertEquals(existingUserProfile.latestUpdateTime(), updatedUserProfile.latestUpdateTime());
        assertEquals(1, updatedUserProfile.userProfileProperties().size());
        assertTrue(updatedUserProfile.userProfileProperties().containsKey(newPropertyName));


    }


    @Test
    public void testExecute_AddValuesToExistingProperty() {
        // Arrange
        UserProfile existingUserProfile = new UserProfile(
                UserId.valueOf("existing-user-id"),
                Instant.parse("2023-06-01T09:16:36.123Z"),
                new HashMap<>()
        );
        existingUserProfile.userProfileProperties().put(
                UserProfilePropertyName.valueOf("existing-property"),
                UserProfilePropertyValue.valueOf(Arrays.asList("value1", "value2"))
        );

        when(userProfileService.get(UserId.valueOf("existing-user-id"))).thenReturn(existingUserProfile);

        Map<UserProfilePropertyName, UserProfilePropertyValue> propertiesToAdd = new HashMap<>();
        UserProfilePropertyName existingPropertyName = UserProfilePropertyName.valueOf("existing-property");
        UserProfilePropertyValue newPropertyValue = UserProfilePropertyValue.valueOf(Arrays.asList("value3", "value4"));
        propertiesToAdd.put(existingPropertyName, newPropertyValue);

        CollectCommand collectCommand = new CollectCommand(
                UserId.valueOf("existing-user-id"),
                propertiesToAdd
        );

        // Act
        executor.execute(collectCommand);

        // Assert
        ArgumentCaptor<UserProfile> userProfileCaptor = ArgumentCaptor.forClass(UserProfile.class);
        verify(userProfileService).update(userProfileCaptor.capture());

        UserProfile updatedUserProfile = userProfileCaptor.getValue();
        assertEquals(UserId.valueOf("existing-user-id"), updatedUserProfile.userId());
        assertEquals(existingUserProfile.latestUpdateTime(), updatedUserProfile.latestUpdateTime());
        assertEquals(1, updatedUserProfile.userProfileProperties().size());
        assertTrue(updatedUserProfile.userProfileProperties().containsKey(existingPropertyName));

        List<String> expectedValues = Arrays.asList("value1", "value2", "value3", "value4");
        assertEquals(expectedValues, updatedUserProfile.userProfileProperties().get(existingPropertyName).getValue());
    }


}

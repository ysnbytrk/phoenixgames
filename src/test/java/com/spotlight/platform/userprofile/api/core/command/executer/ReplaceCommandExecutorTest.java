package com.spotlight.platform.userprofile.api.core.command.executer;

import com.spotlight.platform.userprofile.api.core.commands.ReplaceCommand;
import com.spotlight.platform.userprofile.api.core.commands.executer.ReplaceCommandExecutor;
import com.spotlight.platform.userprofile.api.core.exceptions.EntityNotFoundException;
import com.spotlight.platform.userprofile.api.core.profile.UserProfileService;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReplaceCommandExecutorTest {
    private ReplaceCommandExecutor executor;

    @Mock
    private UserProfileService userProfileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        executor = new ReplaceCommandExecutor(userProfileService);
    }

    @Test
    @DisplayName("Execute ReplaceCommand successfully")
    void executeReplaceCommand() {
        // Prepare test data
        Map<UserProfilePropertyName, UserProfilePropertyValue> profileProperties = new HashMap<>();
        UserProfilePropertyName propertyName = UserProfilePropertyName.valueOf("interests");
        UserProfilePropertyValue propertyValue = UserProfilePropertyValue.valueOf("new-value");
        profileProperties.put(propertyName, propertyValue);

        ReplaceCommand replaceCommand = new ReplaceCommand(UserProfileFixtures.USER_ID, profileProperties);

        UserProfile oldUserProfile = UserProfileFixtures.USER_PROFILE;

        // Mock the behavior of UserProfileService
        when(userProfileService.get(UserProfileFixtures.USER_ID)).thenReturn(oldUserProfile);

        // Execute the command
        executor.execute(replaceCommand);

        // Verify that UserProfileService was called with the correct arguments
        verify(userProfileService).get(UserProfileFixtures.USER_ID);
        verify(userProfileService).update(any(UserProfile.class));

        // Verify the updated user profile
        ArgumentCaptor<UserProfile> userProfileCaptor = ArgumentCaptor.forClass(UserProfile.class);
        verify(userProfileService).update(userProfileCaptor.capture());

        UserProfile updatedUserProfile = userProfileCaptor.getValue();
        assertNotNull(updatedUserProfile);
        assertEquals(UserProfileFixtures.USER_ID, updatedUserProfile.userId());

        // Verify that only the specified properties are replaced
        for (Map.Entry<UserProfilePropertyName, UserProfilePropertyValue> entry : profileProperties.entrySet()) {
            UserProfilePropertyName propertyNameToReplace = entry.getKey();
            UserProfilePropertyValue propertyValueToReplace = entry.getValue();
            assertEquals(propertyValueToReplace, updatedUserProfile.userProfileProperties().get(propertyNameToReplace));
        }
    }


    @Test
    @DisplayName("Execute ReplaceCommand with non-existent user profile")
    void executeReplaceCommandWithNonExistent() {
        // Prepare test data
        Map<UserProfilePropertyName, UserProfilePropertyValue> profileProperties = new HashMap<>();
        UserProfilePropertyName propertyName = UserProfilePropertyName.valueOf("interests");
        UserProfilePropertyValue propertyValue = UserProfilePropertyValue.valueOf("new-value");
        profileProperties.put(propertyName, propertyValue);

        ReplaceCommand replaceCommand = new ReplaceCommand(UserProfileFixtures.USER_ID, profileProperties);


        // Mock the behavior of UserProfileService to throw EntityNotFoundException
        when(userProfileService.get(UserProfileFixtures.USER_ID)).thenThrow(EntityNotFoundException.class);

        // Execute the command
        assertThrows(EntityNotFoundException.class, () -> executor.execute(replaceCommand));

        // Verify that UserProfileService was called with the correct arguments
        verify(userProfileService, times(1)).get(UserProfileFixtures.USER_ID);
        verifyNoMoreInteractions(userProfileService);
    }


}

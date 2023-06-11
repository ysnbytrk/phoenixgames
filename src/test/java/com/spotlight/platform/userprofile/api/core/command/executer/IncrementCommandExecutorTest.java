package com.spotlight.platform.userprofile.api.core.command.executer;

import com.spotlight.platform.userprofile.api.core.commands.IncrementCommand;
import com.spotlight.platform.userprofile.api.core.commands.executer.IncrementCommandExecutor;
import com.spotlight.platform.userprofile.api.core.exceptions.EntityNotFoundException;
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

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IncrementCommandExecutorTest {
    private IncrementCommandExecutor incrementCommandExecutor;

    @Mock
    private UserProfileService userProfileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        incrementCommandExecutor = new IncrementCommandExecutor(userProfileService);
    }

    @Test
    @DisplayName("Execute IncrementCommand successfully")
    void executeIncrementCommand() {
        // Prepare test data
        Map<UserProfilePropertyName, UserProfilePropertyValue> increments = new HashMap<>();
        UserProfilePropertyName propertyName = UserProfilePropertyName.valueOf("counter");
        UserProfilePropertyValue incrementValue = UserProfilePropertyValue.valueOf(1);
        increments.put(propertyName, incrementValue);

        IncrementCommand incrementCommand = new IncrementCommand(UserId.valueOf("user123"), increments);

        UserProfile oldUserProfile = new UserProfile(UserId.valueOf("user123"), null, new HashMap<>());

        // Mock the behavior of UserProfileService
        when(userProfileService.get(UserId.valueOf("user123"))).thenReturn(oldUserProfile);

        // Execute the command
        incrementCommandExecutor.execute(incrementCommand);

        // Verify that UserProfileService was called with the correct arguments
        verify(userProfileService).get(UserId.valueOf("user123"));
        verify(userProfileService).update(any(UserProfile.class));

        // Verify the updated user profile
        ArgumentCaptor<UserProfile> userProfileCaptor = ArgumentCaptor.forClass(UserProfile.class);
        verify(userProfileService).update(userProfileCaptor.capture());

        UserProfile updatedUserProfile = userProfileCaptor.getValue();
        assertNotNull(updatedUserProfile);
        assertEquals(UserId.valueOf("user123"), updatedUserProfile.userId());

        // Verify that the specified properties are incremented
        UserProfilePropertyValue updatedValue = updatedUserProfile.userProfileProperties().get(propertyName);
        assertNotNull(updatedValue);
        assertEquals(1, updatedValue.getValue());
    }

    @Test
    @DisplayName("Execute IncrementCommand with non-existent user profile")
    void executeIncrementCommandWithNonExistent() {
        // Prepare test data
        Map<UserProfilePropertyName, UserProfilePropertyValue> increments = new HashMap<>();
        UserProfilePropertyName propertyName = UserProfilePropertyName.valueOf("counter");
        UserProfilePropertyValue incrementValue = UserProfilePropertyValue.valueOf(1);
        increments.put(propertyName, incrementValue);

        IncrementCommand incrementCommand = new IncrementCommand(UserProfileFixtures.USER_ID, increments);

        // Mock the behavior of UserProfileService to throw EntityNotFoundException
        when(userProfileService.get(UserProfileFixtures.USER_ID)).thenThrow(EntityNotFoundException.class);

        // Execute the command
        assertThrows(EntityNotFoundException.class, () -> incrementCommandExecutor.execute(incrementCommand));

        // Verify that UserProfileService was called with the correct arguments
        verify(userProfileService, times(1)).get(UserProfileFixtures.USER_ID);
        verifyNoMoreInteractions(userProfileService);
    }


}

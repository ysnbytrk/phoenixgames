package com.spotlight.platform.userprofile.api.core.profile;

import com.spotlight.platform.userprofile.api.core.exceptions.EntityNotFoundException;
import com.spotlight.platform.userprofile.api.core.profile.persistence.UserProfileDao;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UserProfileServiceTest {
    @Mock
    private UserProfileDao userProfileDaoMock;

    @InjectMocks
    private UserProfileService userProfileService;
    @Captor
    private ArgumentCaptor<UserProfile> userProfileCaptor;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("get")
    class Get {
        @Test
        void getForExistingUser_returnsUser() {
            when(userProfileDaoMock.get(any(UserId.class))).thenReturn(Optional.of(UserProfileFixtures.USER_PROFILE));

            assertThat(userProfileService.get(UserProfileFixtures.USER_ID)).usingRecursiveComparison()
                    .isEqualTo(UserProfileFixtures.USER_PROFILE);
        }

        @Test
        void getForNonExistingUser_throwsException() {
            when(userProfileDaoMock.get(any(UserId.class))).thenReturn(Optional.empty());

            assertThatThrownBy(() -> userProfileService.get(UserProfileFixtures.USER_ID)).isExactlyInstanceOf(
                    EntityNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("update")
    class Update {
        @Test
        void updateExistingUserProfile() {
            // Create a sample user profile
            UserProfile userProfile = new UserProfile(UserId.valueOf("123"), null, null);

            // Call the update method
            userProfileService.update(userProfile);

            // Verify that the userProfileDaoMock's put method is called with the correct argument
            verify(userProfileDaoMock).put(userProfileCaptor.capture());

            // Verify that the captured argument matches the original user profile
            UserProfile capturedUserProfile = userProfileCaptor.getValue();
            assertThat(capturedUserProfile).isEqualTo(userProfile);
        }
    }
}

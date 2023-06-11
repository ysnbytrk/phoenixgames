package com.spotlight.platform.userprofile.api.model.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class UserProfileTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void serialization_WorksAsExpected() throws Exception {
        String serializedUserProfile = objectMapper.writeValueAsString(UserProfileFixtures.USER_PROFILE);
        final UserProfile userProfileFromFile = objectMapper.readValue(UserProfileFixtures.SERIALIZED_USER_PROFILE, UserProfile.class);
        assertThat(serializedUserProfile).isEqualTo(objectMapper.writeValueAsString(userProfileFromFile));
    }

    @Test
    void deserialization_WorksAsExpected() throws Exception {
        final UserProfile userProfile = objectMapper.readValue(UserProfileFixtures.SERIALIZED_USER_PROFILE, UserProfile.class);
        assertThat(userProfile).isEqualTo(UserProfileFixtures.USER_PROFILE);
    }
}

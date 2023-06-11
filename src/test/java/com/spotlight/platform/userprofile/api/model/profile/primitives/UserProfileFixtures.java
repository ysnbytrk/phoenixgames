package com.spotlight.platform.userprofile.api.model.profile.primitives;

import com.spotlight.platform.helpers.FixtureHelpers;
import com.spotlight.platform.userprofile.api.core.commands.CommandData;
import com.spotlight.platform.userprofile.api.core.commands.CommandType;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;

import java.time.Instant;
import java.util.Map;

public class UserProfileFixtures {
    public static final UserId USER_ID = UserId.valueOf("existing-user-id");
    public static final UserId NON_EXISTING_USER_ID = UserId.valueOf("non-existing-user-id");
    public static final UserId INVALID_USER_ID = UserId.valueOf("invalid-user-id-%");

    public static final Instant LAST_UPDATE_TIMESTAMP = Instant.parse("2021-06-01T09:16:36.123Z");

    public static final Map<UserProfilePropertyName, UserProfilePropertyValue> USER_PROFILE_PROPERTIES = Map.of(UserProfilePropertyName.valueOf("property1"), UserProfilePropertyValue.valueOf("property1Value"));
    public static final UserProfile USER_PROFILE = new UserProfile(USER_ID, LAST_UPDATE_TIMESTAMP,
            USER_PROFILE_PROPERTIES);

    public static final String SERIALIZED_USER_PROFILE = FixtureHelpers.fixture("/fixtures/model/profile/userProfile.json");
    public static final CommandData REPLACE_COMMAND_DATA = new CommandData(USER_ID, CommandType.REPLACE, Map.of(UserProfilePropertyName.valueOf("property1"), UserProfilePropertyValue.valueOf("property1Value")));
    public static final CommandData UNSUPPORTED_COMMAND_DATA = new CommandData(USER_ID, CommandType.UNSUPPORTED, Map.of(UserProfilePropertyName.valueOf("property1"), UserProfilePropertyValue.valueOf("property1Value")));
}

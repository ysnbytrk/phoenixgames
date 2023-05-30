package com.spotlight.platform.userprofile.api.core.profile;

import com.spotlight.platform.userprofile.api.core.exceptions.EntityNotFoundException;
import com.spotlight.platform.userprofile.api.core.profile.persistence.UserProfileDao;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.web.commands.Command;

import javax.inject.Inject;

public class UserProfileService {
    private final UserProfileDao userProfileDao;

    @Inject
    public UserProfileService(UserProfileDao userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

    public UserProfile get(UserId userId) {
        return userProfileDao.get(userId).orElseThrow(EntityNotFoundException::new);
    }

    public void update(UserProfile userProfile) {
        userProfileDao.put(userProfile);
    }

    public void executeCommand(Command command) {
        UserId userId = command.getUserId();
        UserProfile userProfile = userProfileDao.get(userId)
                .orElseThrow(EntityNotFoundException::new);

        final UserProfile executedUserProfile = command.execute(userProfile);

        this.update(executedUserProfile);
    }


}

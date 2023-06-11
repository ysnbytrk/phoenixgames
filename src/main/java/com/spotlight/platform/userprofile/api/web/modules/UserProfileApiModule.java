package com.spotlight.platform.userprofile.api.web.modules;

import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * Guice module for configuring the UserProfile API dependencies.
 */
public class UserProfileApiModule implements Module {
    /**
     * Configures the UserProfile API dependencies by installing the required modules.
     *
     * @param binder The Guice binder.
     */
    @Override
    public void configure(Binder binder) {
        binder.install(new JsonModule());
        binder.install(new ProfileModule());
    }
}

package com.spotlight.platform.userprofile.api.web.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.spotlight.platform.userprofile.api.core.commands.CommandType;
import com.spotlight.platform.userprofile.api.core.commands.executer.*;
import com.spotlight.platform.userprofile.api.core.commands.executer.annotation.ExecuterQualifier;
import com.spotlight.platform.userprofile.api.core.commands.executer.annotation.ExecutorsMap;
import com.spotlight.platform.userprofile.api.core.profile.UserProfileService;
import com.spotlight.platform.userprofile.api.core.profile.persistence.UserProfileDao;
import com.spotlight.platform.userprofile.api.core.profile.persistence.UserProfileDaoInMemory;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Guice module for configuring the profile-related dependencies.
 */
public class ProfileModule extends AbstractModule {
    /**
     * Configures the bindings for the profile-related dependencies.
     */
    @Override
    protected void configure() {
        bind(UserProfileDao.class).to(UserProfileDaoInMemory.class).in(Singleton.class);
        bind(UserProfileService.class).in(Singleton.class);
    }

    /**
     * Provides a singleton instance of the CommandExecutorRegistry.
     *
     * @param injector The Guice injector.
     * @return The CommandExecutorRegistry instance.
     */
    @Provides
    @Singleton
    public CommandExecutorRegistry provideCommandExecutorRegistry(Injector injector) {
        CommandExecutorRegistry registry = new CommandExecutorRegistry();
        registry.registerExecutor(CommandType.REPLACE, injector.getInstance(ReplaceCommandExecutor.class));
        registry.registerExecutor(CommandType.INCREMENT, injector.getInstance(IncrementCommandExecutor.class));
        registry.registerExecutor(CommandType.COLLECT, injector.getInstance(CollectCommandExecutor.class));
        return registry;
    }

    /**
     * Provides a singleton instance of the command executors map.
     *
     * @param injector The Guice injector.
     * @return The map of command types to command executors.
     */
    @Provides
    @Singleton
    @ExecutorsMap
    public Map<CommandType, CommandExecutor> provideCommandExecutorsMap(Injector injector) {
        Map<CommandType, CommandExecutor> executorMap = new HashMap<>();
        executorMap.put(CommandType.REPLACE, injector.getInstance(ReplaceCommandExecutor.class));
        executorMap.put(CommandType.INCREMENT, injector.getInstance(IncrementCommandExecutor.class));
        executorMap.put(CommandType.COLLECT, injector.getInstance(CollectCommandExecutor.class));
        return executorMap;
    }

    /**
     * Creates a custom annotation for command executor qualifier based on the command type.
     *
     * @param commandType The command type.
     * @return The custom annotation for command executor qualifier.
     */
    private Annotation createCommandExecutorQualifier(CommandType commandType) {
        return new ExecuterQualifier() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return ExecuterQualifier.class;
            }

            @Override
            public CommandType value() {
                return commandType;
            }
        };
    }
}

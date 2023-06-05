package com.spotlight.platform.userprofile.api.web.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.spotlight.platform.userprofile.api.core.commands.CommandType;
import com.spotlight.platform.userprofile.api.core.commands.executer.*;
import com.spotlight.platform.userprofile.api.core.commands.executer.annotation.ExecuterQualifier;
import com.spotlight.platform.userprofile.api.core.commands.executer.annotation.ExecutorsMap;
import com.spotlight.platform.userprofile.api.core.profile.UserProfileService;
import com.spotlight.platform.userprofile.api.core.profile.persistence.UserProfileDao;
import com.spotlight.platform.userprofile.api.core.profile.persistence.UserProfileDaoInMemory;

import javax.inject.Singleton;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class ProfileModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserProfileDao.class).to(UserProfileDaoInMemory.class).in(Singleton.class);
        bind(UserProfileService.class).in(Singleton.class);
        bind(CommandExecutorRegistry.class).in(Singleton.class);

        // Bind command executors
        bind(CommandExecutor.class)
                .annotatedWith(createCommandExecutorQualifier(CommandType.REPLACE))
                .to(ReplaceCommandExecutor.class)
                .in(Singleton.class);
        bind(CommandExecutor.class)
                .annotatedWith(createCommandExecutorQualifier(CommandType.INCREMENT))
                .to(IncrementCommandExecutor.class)
                .in(Singleton.class);
        bind(CommandExecutor.class)
                .annotatedWith(createCommandExecutorQualifier(CommandType.COLLECT))
                .to(CollectCommandExecutor.class)
                .in(Singleton.class);

        // Create a map to hold the command executors
        Map<CommandType, CommandExecutor> executorMap = new HashMap<>();
        executorMap.put(CommandType.REPLACE, getInstance(ReplaceCommandExecutor.class));
        executorMap.put(CommandType.INCREMENT, getInstance(IncrementCommandExecutor.class));
        executorMap.put(CommandType.COLLECT, getInstance(CollectCommandExecutor.class));

        // Bind the map of executors to the registry
        bind(new TypeLiteral<Map<CommandType, CommandExecutor>>() {
        })
                .annotatedWith(ExecutorsMap.class)
                .toInstance(executorMap);
    }

    private Annotation createCommandExecutorQualifier(CommandType commandType) {
        return new ExecutorQualifierImpl(commandType);
    }

    private <T> T getInstance(Class<T> type) {
        try {
            return binder().getProvider(type).get();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get instance for type: " + type, e);
        }
    }

    private record ExecutorQualifierImpl(CommandType value) implements ExecuterQualifier {

        @Override
            public Class<? extends Annotation> annotationType() {
                return ExecutorQualifierImpl.class;
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                ExecutorQualifierImpl other = (ExecutorQualifierImpl) obj;
                return value == other.value;
            }

    }
}


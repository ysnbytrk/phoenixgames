package com.spotlight.platform.userprofile.api.web.resources;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.spotlight.platform.userprofile.api.core.commands.Command;
import com.spotlight.platform.userprofile.api.core.commands.CommandData;
import com.spotlight.platform.userprofile.api.core.commands.CommandType;
import com.spotlight.platform.userprofile.api.core.commands.executer.*;
import com.spotlight.platform.userprofile.api.core.commands.executer.annotation.ExecutorsMap;
import com.spotlight.platform.userprofile.api.core.profile.persistence.UserProfileDao;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import com.spotlight.platform.userprofile.api.web.UserProfileApiApplication;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.vyarus.dropwizard.guice.test.ClientSupport;
import ru.vyarus.dropwizard.guice.test.jupiter.ext.TestDropwizardAppExtension;

import javax.ws.rs.client.Entity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Execution(ExecutionMode.SAME_THREAD)
class CommandResourceIntegrationTest {
    @RegisterExtension
    static TestDropwizardAppExtension APP = TestDropwizardAppExtension.forApp(UserProfileApiApplication.class)
            .randomPorts()
            .hooks(builder -> builder.modulesOverride(new AbstractModule() {
                @Provides
                @Singleton
                public CommandExecutorRegistry provideCommandExecutorRegistry(Injector injector) {
                    CommandExecutorRegistry registry = new CommandExecutorRegistry();
                    registry.registerExecutor(CommandType.REPLACE, injector.getInstance(ReplaceCommandExecutor.class));
                    registry.registerExecutor(CommandType.INCREMENT, injector.getInstance(IncrementCommandExecutor.class));
                    registry.registerExecutor(CommandType.COLLECT, injector.getInstance(CollectCommandExecutor.class));
                    return registry;
                }

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

                @Provides
                @Singleton
                public ReplaceCommandExecutor getReplaceCommandExecutor() {
                    return mock(ReplaceCommandExecutor.class);
                }

                @Provides
                @Singleton
                public IncrementCommandExecutor getIncrementCommandExecutor() {
                    return mock(IncrementCommandExecutor.class);
                }

                @Provides
                @Singleton
                public CollectCommandExecutor getCollectCommandExecutor() {
                    return mock(CollectCommandExecutor.class);
                }

                @Provides
                @Singleton
                public UserProfileDao getUserProfileDao() {
                    return mock(UserProfileDao.class);
                }
            }))
            .randomPorts()
            .create();

    @BeforeEach
    void beforeEach(ReplaceCommandExecutor replaceCommandExecutor,
                    IncrementCommandExecutor incrementCommandExecutor,
                    CollectCommandExecutor collectCommandExecutor,
                    UserProfileDao userProfileDao) {
        reset(replaceCommandExecutor);
        reset(incrementCommandExecutor);
        reset(collectCommandExecutor);
        reset(userProfileDao);
    }

    @Nested
    @DisplayName("handleCommands")
    class HandleCommands {
        private static final String URL = "/commands";

        @Test
        void multipleCommands_success(ClientSupport client,
                                      ReplaceCommandExecutor replaceCommandExecutor,
                                      IncrementCommandExecutor incrementCommandExecutor,
                                      CollectCommandExecutor collectCommandExecutor,
                                      UserProfileDao userProfileDao) {
            when(userProfileDao.get(any(UserId.class))).thenReturn(Optional.of(UserProfileFixtures.USER_PROFILE));
            var response = client.targetRest().path(URL).request()
                    .post(Entity.json(List.of(
                            new CommandData(UserProfileFixtures.USER_ID, CommandType.REPLACE, Map.of(
                                    UserProfilePropertyName.valueOf("currentGold"), UserProfilePropertyValue.valueOf("500"),
                                    UserProfilePropertyName.valueOf("currentGems"), UserProfilePropertyValue.valueOf("800")
                            )),
                            new CommandData(UserProfileFixtures.USER_ID, CommandType.INCREMENT, Map.of(
                                    UserProfilePropertyName.valueOf("battleFought"), UserProfilePropertyValue.valueOf("10"),
                                    UserProfilePropertyName.valueOf("questsNotCompleted"), UserProfilePropertyValue.valueOf("-1")
                            )),
                            new CommandData(UserProfileFixtures.USER_ID, CommandType.COLLECT, Map.of(
                                    UserProfilePropertyName.valueOf("inventory"), UserProfilePropertyValue.valueOf(List.of("sword1", "sword2", "shield1")),
                                    UserProfilePropertyName.valueOf("tools"), UserProfilePropertyValue.valueOf(List.of("tool1", "tool2"))
                            ))
                    )));

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK_200);

            // Verify that the replaceCommandExecutor was called with the appropriate command
            verify(replaceCommandExecutor).execute(any(Command.class));
            verify(incrementCommandExecutor).execute(any(Command.class));
            verify(collectCommandExecutor).execute(any(Command.class));
        }


    }
}

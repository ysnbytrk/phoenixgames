package com.spotlight.platform.userprofile.api.core.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.spotlight.platform.userprofile.api.core.commands.CommandType;
import com.spotlight.platform.userprofile.api.core.commands.executer.CommandExecutor;
import com.spotlight.platform.userprofile.api.core.commands.executer.CommandExecutorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ApplicationConfiguration {

    @Autowired
    @Qualifier("ReplaceCommandExecutor")
    private CommandExecutor replaceCommandExecutor;

    @Autowired
    @Qualifier("IncrementCommandExecutor")
    private CommandExecutor incrementCommandExecutor;

    @Autowired
    @Qualifier("CollectCommandExecutor")
    private CommandExecutor collectCommandExecutor;

    /**
     * Creates a registry for command executors and registers the available executors.
     *
     * @return CommandExecutorRegistry instance.
     */
    @Bean
    public CommandExecutorRegistry commandExecutorRegistry() {
        CommandExecutorRegistry registry = new CommandExecutorRegistry();
        registry.registerExecutor(CommandType.REPLACE, replaceCommandExecutor);
        registry.registerExecutor(CommandType.INCREMENT, incrementCommandExecutor);
        registry.registerExecutor(CommandType.COLLECT, collectCommandExecutor);
        return registry;
    }

    /**
     * Creates a map of command executors with their associated command types.
     *
     * @return Map of CommandType to CommandExecutor.
     */
    @Bean(name = "commandExecutorsMap")
    public Map<CommandType, CommandExecutor> commandExecutorsMap() {
        Map<CommandType, CommandExecutor> executorMap = new HashMap<>();
        executorMap.put(CommandType.REPLACE, replaceCommandExecutor);
        executorMap.put(CommandType.INCREMENT, incrementCommandExecutor);
        executorMap.put(CommandType.COLLECT, collectCommandExecutor);
        return executorMap;
    }

    /**
     * Creates and configures an instance of ObjectMapper for JSON serialization and deserialization.
     *
     * @return Configured ObjectMapper instance.
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

}

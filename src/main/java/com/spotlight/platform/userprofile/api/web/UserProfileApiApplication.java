package com.spotlight.platform.userprofile.api.web;

import com.spotlight.platform.userprofile.api.core.json.JsonMapper;
import com.spotlight.platform.userprofile.api.model.configuration.UserProfileApiConfiguration;
import com.spotlight.platform.userprofile.api.web.exceptionmappers.EntityNotFoundExceptionMapper;
import com.spotlight.platform.userprofile.api.web.healthchecks.PreventStartupWarningHealthCheck;
import com.spotlight.platform.userprofile.api.web.modules.UserProfileApiModule;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;

/**
 * Main application class for the UserProfile API.
 */
public class UserProfileApiApplication extends Application<UserProfileApiConfiguration> {

    private GuiceBundle guiceBundle;

    @Override
    public String getName() {
        return UserProfileApiConfiguration.APPLICATION_NAME;
    }

    @Override
    public void initialize(Bootstrap<UserProfileApiConfiguration> bootstrap) {
        super.initialize(bootstrap);

        // Create and configure the Guice bundle
        guiceBundle = GuiceBundle.builder()
                .enableAutoConfig(getClass().getPackage().getName())
                .modules(new UserProfileApiModule())
                .printDiagnosticInfo()
                .build();

        // Add the Guice bundle to the Dropwizard bootstrap
        bootstrap.addBundle(guiceBundle);

        // Set the custom ObjectMapper for JSON serialization/deserialization
        bootstrap.setObjectMapper(JsonMapper.getInstance());
    }

    @Override
    public void run(UserProfileApiConfiguration configuration, Environment environment) {
        // Register health checks and exception mappers
        registerHealthChecks(environment);
        registerExceptionMappers(environment);
    }

    /**
     * Main method to start the UserProfile API application.
     *
     * @param args Command-line arguments.
     * @throws Exception if an error occurs while running the application.
     */
    public static void main(String[] args) throws Exception {
        new UserProfileApiApplication().run(args);
    }

    /**
     * Registers health checks in the Dropwizard environment.
     *
     * @param environment The Dropwizard environment.
     */
    private void registerHealthChecks(Environment environment) {
        environment.healthChecks().register(PreventStartupWarningHealthCheck.NAME, getInstance(PreventStartupWarningHealthCheck.class));
    }

    /**
     * Registers exception mappers in the Dropwizard environment.
     *
     * @param environment The Dropwizard environment.
     */
    private void registerExceptionMappers(Environment environment) {
        environment.jersey().register(getInstance(EntityNotFoundExceptionMapper.class));
    }

    /**
     * Retrieves an instance of the specified class from the Guice injector.
     *
     * @param clazz The class of the instance to retrieve.
     * @param <T>   The type of the instance to retrieve.
     * @return The instance of the specified class.
     */
    private <T> T getInstance(Class<T> clazz) {
        return guiceBundle.getInjector().getInstance(clazz);
    }
}

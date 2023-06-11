package com.spotlight.platform.userprofile.api.web.healthchecks;

import com.codahale.metrics.health.HealthCheck;

/**
 * Health check class to prevent the startup warning message.
 * This health check always returns a healthy status.
 */
public class PreventStartupWarningHealthCheck extends HealthCheck {
    public static final String NAME = "preventing-startup-warning-healthcheck";

    /**
     * Performs the health check.
     *
     * @return The result of the health check, always healthy.
     */
    @Override
    protected Result check() {
        return Result.healthy();
    }
}

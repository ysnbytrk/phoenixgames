package com.spotlight.platform.userprofile.api.core.commands.executer.annotation;

import com.spotlight.platform.userprofile.api.core.commands.CommandType;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * An annotation used to qualify command executors based on their command type.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ExecuterQualifier {

    /**
     * The command type to associate with the executor.
     *
     * @return The command type value.
     */
    CommandType value();
}

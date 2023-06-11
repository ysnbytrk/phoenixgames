package com.spotlight.platform.userprofile.api.core.commands.executer.annotation;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * An annotation used to mark a map of command executors.
 */
@BindingAnnotation
@Retention(RetentionPolicy.RUNTIME)
public @interface ExecutorsMap {

    /**
     * This annotation serves as a marker for a map of command executors.
     * It can be used to differentiate the map bindings when using dependency injection frameworks like Google Guice.
     * The annotation is retained at runtime (`RetentionPolicy.RUNTIME`) so that it can be accessed and used through reflection.
     */
}

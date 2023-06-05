package com.spotlight.platform.userprofile.api.core.commands.executer.annotation;

import com.spotlight.platform.userprofile.api.core.commands.CommandType;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ExecuterQualifier {
    CommandType value();
}

package com.epam.aa.sportfinder.controller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerAction {
    String path();

    String method();

    boolean autogenerateSimpleGet() default false;
}

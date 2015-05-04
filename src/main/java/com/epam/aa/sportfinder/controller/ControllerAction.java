package com.epam.aa.sportfinder.controller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerAction {
    String path();

    String method();


    class HttpMethod {
        public static final String POST = "POST";
        public static final String GET = "GET";
    }
}

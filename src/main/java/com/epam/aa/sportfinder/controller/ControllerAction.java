package com.epam.aa.sportfinder.controller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerAction {
    /**
     * Path is part of request URI that follows after /controller part of the URI
     * and before any get parameters, i.e. before ?
     * @return
     */
    String path();

    String httpMethod();

    String[] accessDeniedTo() default {};

    class HttpMethod {
        public static final String POST = "POST";
        public static final String GET = "GET";
    }

    class Permission {
        public static final String GUEST = "guest";
        public static final String CUSTOMER = "customer";
        public static final String MANAGER = "manager";
    }
}

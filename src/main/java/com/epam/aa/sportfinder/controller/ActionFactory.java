package com.epam.aa.sportfinder.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private static final Logger logger = LoggerFactory.getLogger(ActionFactory.class);

    private static final Map<String, Action> actions = initActionFactory();

    public static Action getAction(HttpServletRequest request) {
        logger.debug("Retrieving action according to {}{}", request.getMethod(), request.getRequestURI());
        return actions.get(request.getMethod() + request.getPathInfo());
    }

    //TODO: populate map using reflections and annotations
    private static Map<String, Action> initActionFactory() {
        Map<String, Action> actions = new HashMap<>();
        actions.put("GET/login", (request) -> "login");
        actions.put("POST/login", new LoginAction());
        actions.put("GET/manager/register", (request) -> "manager/register");
        actions.put("POST/manager/register", new ManagerRegisterAction());
        actions.put("GET/manager/home", (request) -> "manager/home");
//        actions.put("GET/logout", new LogoutAction());
        return actions;
    }
}
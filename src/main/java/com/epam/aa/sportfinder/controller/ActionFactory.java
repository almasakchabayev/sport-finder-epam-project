package com.epam.aa.sportfinder.controller;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ActionFactory {
    private static final Logger logger = LoggerFactory.getLogger(ActionFactory.class);

    private static final Map<String, Action> actions = initActionFactory();

    public static Action getAction(HttpServletRequest request) {
        logger.debug("Retrieving action according to {}{}", request.getMethod(), request.getRequestURI());
        String path = request.getRequestURI().replace("/controller", "");
        return actions.get(request.getMethod() + path);
    }

    private static Map<String, Action> initActionFactory() {
        Map<String, Action> actions = new HashMap<>();

        Reflections reflections = new Reflections("com.epam.aa.sportfinder.controller");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(ControllerAction.class);

        for (Class<?> action : annotated) {
            ControllerAction annotation = action.getAnnotation(ControllerAction.class);
            String path = annotation.path();
            String method = annotation.method();
            boolean autogenerateSimpleGet = annotation.autogenerateSimpleGet();

            String actionFullPath = method + path;

            try {
                actions.put(actionFullPath, (Action) action.newInstance());
                logger.info("added action for {}", actionFullPath);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new ControllerException("Could not initialise ActionFactory", e);
            }

            if (autogenerateSimpleGet){
                String simpleGetPath = "GET" + path;
                actions.put(simpleGetPath, (request) -> path);
                logger.info("added action for {}", simpleGetPath);
            }
        }

        // Manually adding simple actions
        actions.put("GET/register", (request) -> "register");
//        actions.put("GET/logout", new LogoutAction());
        return actions;
    }
}
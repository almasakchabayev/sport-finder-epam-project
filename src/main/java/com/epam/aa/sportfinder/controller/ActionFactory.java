package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.config.ControllerActionsLoader;
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
        String path = getPath(request);
        return actions.get(request.getMethod() + path);
    }

    private static Map<String, Action> initActionFactory() {
        Map<String, Action> actions = new HashMap<>();

        Set<Class<?>> annotated = ControllerActionsLoader.getClassesAnnotatedWithControllerAction();
        for (Class<?> action : annotated) {
            ControllerAction annotation = action.getAnnotation(ControllerAction.class);
            String path = annotation.path();
            String method = annotation.httpMethod();

            String actionFullPath = method + path;

            try {
                actions.put(actionFullPath, (Action) action.newInstance());
                logger.info("added action for {}", actionFullPath);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new ControllerException("Could not initialize ActionFactory", e);
            }
        }
        //TODO: add logout
//        actions.put("GET/logout", new LogoutAction());
        return actions;
    }

    public static String getPath(HttpServletRequest request) {
        String path = request.getRequestURI().replace("/controller", "");
        if (path.contains(";")) {
            int i = path.indexOf(";");
            path = path.substring(0, i);
        }
        return path;
    }
}
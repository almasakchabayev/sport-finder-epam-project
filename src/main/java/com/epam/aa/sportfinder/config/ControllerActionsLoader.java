package com.epam.aa.sportfinder.config;

import com.epam.aa.sportfinder.controller.ControllerAction;
import org.reflections.Reflections;

import java.util.Set;

public class ControllerActionsLoader {
    private static Set<Class<?>> annotatedClasses = loadClassesAnnotatedAsControllerAction();

    private static Set<Class<?>> loadClassesAnnotatedAsControllerAction() {
        Reflections reflections = new Reflections("com.epam.aa.sportfinder.controller");
        return reflections.getTypesAnnotatedWith(ControllerAction.class);
    }

    public static Set<Class<?>> getClassesAnnotatedWithControllerAction() {
        return annotatedClasses;
    }
}

package com.epam.aa.sportfinder.controller;

import javax.servlet.http.HttpServletRequest;

import static com.epam.aa.sportfinder.controller.ControllerAction.*;
import static com.epam.aa.sportfinder.controller.ControllerAction.HttpMethod;

@ControllerAction(path = "/manager/profile",
        httpMethod = HttpMethod.GET,
        accessDeniedTo = {Permission.GUEST})
public class ManagerProfileGetAction implements Action {
    public String execute(HttpServletRequest request) throws ControllerException {
        return "manager/profile";
    }
}
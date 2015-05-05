package com.epam.aa.sportfinder.controller;

import javax.servlet.http.HttpServletRequest;

import static com.epam.aa.sportfinder.controller.ControllerAction.*;

@ControllerAction(path = "/login",
        httpMethod = HttpMethod.GET,
        accessDeniedTo = {Permission.MANAGER, Permission.CUSTOMER})
public class LoginGetAction implements Action {
    public String execute(HttpServletRequest request) throws ControllerException {
        return "login";
    }
}
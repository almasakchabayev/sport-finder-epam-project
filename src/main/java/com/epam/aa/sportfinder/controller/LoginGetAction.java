package com.epam.aa.sportfinder.controller;

import javax.servlet.http.HttpServletRequest;

@ControllerAction(path = "/login", method = ControllerAction.HttpMethod.GET)
public class LoginGetAction implements Action {
    public String execute(HttpServletRequest request) throws ControllerException {
        return "login";
    }
}
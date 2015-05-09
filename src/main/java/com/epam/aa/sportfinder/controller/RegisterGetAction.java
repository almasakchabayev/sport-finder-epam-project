package com.epam.aa.sportfinder.controller;

import javax.servlet.http.HttpServletRequest;

import static com.epam.aa.sportfinder.controller.ControllerAction.*;

@ControllerAction(path = "/register",
        httpMethod = HttpMethod.GET)
public class RegisterGetAction implements Action {
    public String execute(HttpServletRequest request) throws ControllerException {
        return "register";
    }
}
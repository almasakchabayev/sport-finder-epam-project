package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.model.User;
import com.epam.aa.sportfinder.service.ManagerService;

import javax.servlet.http.HttpServletRequest;

@ControllerAction(path="/login", method="GET")
public class LoginGetAction implements Action {
    public String execute(HttpServletRequest request) throws ControllerException {
        return "login";
    }
}
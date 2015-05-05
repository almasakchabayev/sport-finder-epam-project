package com.epam.aa.sportfinder.controller;

import javax.servlet.http.HttpServletRequest;

import static com.epam.aa.sportfinder.controller.ControllerAction.*;

@ControllerAction(path = "/manager/register",
        httpMethod = HttpMethod.GET,
        accessDeniedTo = {Permission.MANAGER})
public class ManagerRegisterGetAction implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        return "manager/register";
    }
}

package com.epam.aa.sportfinder.controller;

import javax.servlet.http.HttpServletRequest;

import static com.epam.aa.sportfinder.controller.ControllerAction.*;

@ControllerAction(path = "/manager/register",
        httpMethod = HttpMethod.GET,
        accessAllowedTo = {AuthenticatedAs.GUEST, AuthenticatedAs.CUSTOMER})
public class ManagerRegisterGetAction implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        return "manager/register";
    }
}

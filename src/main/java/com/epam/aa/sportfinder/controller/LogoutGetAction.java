package com.epam.aa.sportfinder.controller;

import javax.servlet.http.HttpServletRequest;

import static com.epam.aa.sportfinder.controller.ControllerAction.*;
import static com.epam.aa.sportfinder.controller.ControllerAction.HttpMethod;

@ControllerAction(path = "/logout",
        httpMethod = HttpMethod.GET,
        accessDeniedTo = {Permission.GUEST})
public class LogoutGetAction implements Action {
    public String execute(HttpServletRequest request) throws ControllerException {
        request.getSession().invalidate();
        return "redirect:/index";
    }
}
package com.epam.aa.sportfinder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ControllerAction(path="manager/home", method="GET")
public class ManagerHomeAction implements Action {
    @Override
    public String execute(HttpServletRequest request) throws ControllerException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "manager/home";
    }
}

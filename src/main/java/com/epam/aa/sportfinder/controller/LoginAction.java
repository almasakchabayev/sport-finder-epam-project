package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.model.Manager;
import com.epam.aa.sportfinder.service.ManagerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAction implements Action {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Manager manager = ManagerService.findByCredentials(email, password);
        if (manager != null) {
            request.getSession().setAttribute("manager", manager);
            return "home";
        } else {
            request.setAttribute("error", "Unknown username/password. Please retry.");
            return "login";
        }
    }
}

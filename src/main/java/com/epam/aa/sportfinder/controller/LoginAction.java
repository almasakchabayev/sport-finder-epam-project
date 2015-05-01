package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.model.Manager;
import com.epam.aa.sportfinder.service.ManagerService;

import javax.servlet.HttpMethodConstraintElement;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAction implements Action {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ControllerException {
        String email = request.getParameter("form-login-email");
        String password = request.getParameter("form-login-password");
        Manager manager = ManagerService.findByCredentials(email, password);
        if (manager != null) {
            request.getSession().setAttribute("manager", manager);
            return "manager-home";
        }

//        Customer customer = CustomerService.findByCredentials(email, password);

        request.setAttribute("error", "Unknown username/password. Please retry.");
        return "login";
    }
}

package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.model.Manager;
import com.epam.aa.sportfinder.service.ManagerService;

import javax.servlet.http.HttpServletRequest;

@ControllerAction(path="/login", method="POST", autogenerateSimpleGet=true)
public class LoginAction implements Action {
    public String execute(HttpServletRequest request) throws ControllerException {
        String email = request.getParameter("form-login-email");
        String password = request.getParameter("form-login-password");
        Manager manager = ManagerService.findByCredentials(email, password);
        if (manager != null) {
            return loginManager(request, manager);
        }

//        Customer customer = CustomerService.findByCredentials(email, password);

        request.setAttribute("error", "Unknown username/password. Please retry.");
        return "login";
    }

    protected static String loginManager(HttpServletRequest request, Manager manager) {
        request.getSession().setAttribute("user", manager);
        return "redirect:/manager/items";
    }
}

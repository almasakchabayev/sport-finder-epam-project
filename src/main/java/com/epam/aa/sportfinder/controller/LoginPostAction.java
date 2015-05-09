package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.model.User;
import com.epam.aa.sportfinder.service.ManagerService;

import javax.servlet.http.HttpServletRequest;

import static com.epam.aa.sportfinder.controller.ControllerAction.*;

@ControllerAction(path = "/login",
        httpMethod = HttpMethod.POST,
        accessDeniedTo = {Permission.MANAGER, Permission.CUSTOMER})
public class LoginPostAction implements Action {
    public String execute(HttpServletRequest request) throws ControllerException {
        String email = request.getParameter("form-login-email");
        String password = request.getParameter("form-login-password");
        User manager = ManagerService.findByCredentials(email, password);
        if (manager != null) {
            request.getSession().setAttribute("user", manager);
            return "redirect:/manager/items";
        }

        //TODO; add login for customer
//        Customer customer = CustomerService.findByCredentials(email, password);

        request.setAttribute("error", "Unknown username/password. Please retry.");
        return "login";
    }
}

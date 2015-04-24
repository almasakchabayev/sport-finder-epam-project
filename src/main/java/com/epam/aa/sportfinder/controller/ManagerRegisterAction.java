package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.model.Manager;
import com.epam.aa.sportfinder.model.PhoneNumber;
import com.epam.aa.sportfinder.service.ManagerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManagerRegisterAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String firstName = request.getParameter("form-register-first-name");
        String lastName = request.getParameter("form-register-last-name");
        String email = request.getParameter("form-register-email");
        String number = request.getParameter("form-register-phone-number");
        String password = request.getParameter("form-register-password");
        String confirmPassword = request.getParameter("form-register-confirm-password");

        Manager manager = new Manager();
        manager.setFirstName(firstName);
        manager.setLastName(lastName);
        manager.setEmail(email);
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setNumber(number);
        manager.addPhoneNumber(phoneNumber);
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "password and confirm-password are not the same");
            return "/customer-register";
        }
        manager.setPassword(password);

        //TODO: how to catch validation exceptions and show them to user?
//        Validator.validate(customer);

        ManagerService.create(manager);
        return "managerhome";
    }
}

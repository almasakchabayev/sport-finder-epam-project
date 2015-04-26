package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.model.Address;
import com.epam.aa.sportfinder.model.Company;
import com.epam.aa.sportfinder.model.Manager;
import com.epam.aa.sportfinder.model.PhoneNumber;
import com.epam.aa.sportfinder.service.ManagerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class ManagerRegisterAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String firstName = request.getParameter("form-register-first-name");
        String lastName = request.getParameter("form-register-last-name");
        String email = request.getParameter("form-register-email");
        String number = request.getParameter("form-register-phone-number");
        String number2 = request.getParameter("form-register-phone-number-2");
        String password = request.getParameter("form-register-password");
        String confirmPassword = request.getParameter("form-register-confirm-password");
        String companyName = request.getParameter("form-register-company-name");
        String companyCountry = request.getParameter("form-register-company-country");
        String companyCity = request.getParameter("form-register-company-city");
        String companyAddressLine1 = request.getParameter("form-register-company-address-line-1");
        String companyAddressLine2 = request.getParameter("form-register-company-address-line-2");
        String companyZipcode = request.getParameter("form-register-company-zipcode");


        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "password and confirm-password are not the same");
            return "/manager-register";
        }
        Address address = new Address();
        address.setCountry(companyCountry);
        address.setCity(companyCity);
        address.setAddressLine1(companyAddressLine1);
        address.setAddressLine2(companyAddressLine2);
        address.setZipcode(companyZipcode);

        Company company = new Company();
        company.setAddress(address);
        company.setName(companyName);

        Manager manager = new Manager();
        manager.setCompany(company);
        manager.setFirstName(firstName);
        manager.setLastName(lastName);
        manager.setEmail(email);
        PhoneNumber phoneNumber = new PhoneNumber(number);
        manager.addPhoneNumber(phoneNumber);
        if (!number2.equals("")) {
            PhoneNumber phoneNumber2 = new PhoneNumber(number2);
            manager.addPhoneNumber(phoneNumber2);
        }
        manager.setPassword(password);

        //TODO: how to catch validation exceptions and show them to user?
//        Validator.validate(customer);

        ManagerService.create(manager);
        return "manager-home";
    }
}

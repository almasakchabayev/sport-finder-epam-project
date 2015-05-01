package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.model.Address;
import com.epam.aa.sportfinder.model.Company;
import com.epam.aa.sportfinder.model.Manager;
import com.epam.aa.sportfinder.model.PhoneNumber;
import com.epam.aa.sportfinder.service.ManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ManagerRegisterAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(ManagerRegisterAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Starting manager register action");
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

        logger.debug("retrieved data from web form");

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
        if (!password.equals(confirmPassword))
            return returnError(request, manager, "password and confirm-password are not the same");

        manager.setPassword(password);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Manager>> violations = validator.validate(manager);
        if (violations.size() > 0 )
            return returnError(request, manager, violations.iterator().next().getMessage());

        logger.debug("Validation finished successfully");
        ManagerService.create(manager);
        return "redirect:manager/home";
    }

    private String returnError(HttpServletRequest request, Manager manager, String errorMessage) {
        request.setAttribute("error", errorMessage);
        request.setAttribute("manager", manager);
        return "manager/register";
    }
}

package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.model.*;
import com.epam.aa.sportfinder.service.ManagerService;
import com.epam.aa.sportfinder.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.epam.aa.sportfinder.controller.ControllerAction.HttpMethod;
import static com.epam.aa.sportfinder.controller.ControllerAction.Permission;

@ControllerAction(path = "/manager/profile",
        httpMethod = HttpMethod.POST,
        accessDeniedTo = {Permission.GUEST})
public class ManagerProfilePostAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(ManagerProfilePostAction.class);

    @Override
    public String execute(HttpServletRequest request) {
        logger.debug("Changing manager info");
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        String email = request.getParameter("email");
        String number = request.getParameter("phone-number");
        String number2 = request.getParameter("phone-number-2");
        String companyName = request.getParameter("company-name");
        String companyCountry = request.getParameter("country");
        String companyCity = request.getParameter("city");
        String companyAddressLine1 = request.getParameter("address-line-1");
        String companyAddressLine2 = request.getParameter("address-line-2");
        String companyZipcode = request.getParameter("zipcode");

        logger.debug("retrieved data from web form");

        Manager manager = (Manager) request.getSession().getAttribute("user");

        manager.getCompany().getAddress().setCountry(companyCountry);
        manager.getCompany().getAddress().setCity(companyCity);
        manager.getCompany().getAddress().setAddressLine1(companyAddressLine1);
        manager.getCompany().getAddress().setAddressLine2(companyAddressLine2);
        manager.getCompany().getAddress().setZipcode(companyZipcode);
        manager.getCompany().setName(companyName);
        manager.setFirstName(firstName);
        manager.setLastName(lastName);
        manager.setEmail(email);
        manager.getPhoneNumbers().get(0).setNumber(number);
        if (number2 != null && !number2.equals("")) {
            manager.getPhoneNumbers().get(1).setNumber(number2);
        }

        Map<String, String> errors = new HashMap<>();
        try {
            Part filePart = request.getPart("profile-picture");
            if (filePart != null && filePart.getSize() > 0) {
                String type = filePart.getContentType();
                if (type.matches("image/(jpeg|jpg|png|gif)")) {
                    InputStream inputStream = filePart.getInputStream();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] tmp = new byte[4096];
                    int ret;
                    while((ret = inputStream.read(tmp)) > 0) {
                        bos.write(tmp, 0, ret);
                    }

                    if (manager.getImage() == null) manager.setImage(new Image());
                    manager.getImage().setImageArray(bos.toByteArray());
                } else {
                    errors.put("image", "image is of not appropriate format, the allowed formats are jpg, png, gif");
                }
            }
        } catch (IOException | ServletException e) {
            throw new ServiceException("error when processing part form-register-image");
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Manager>> violations = validator.validate(manager);
        if (violations.size() > 0 ) {
            for (ConstraintViolation<Manager> violation : violations) {
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
        }
        if (errors.size() > 0)
            return returnError(request, manager, errors);

        logger.debug("Validation finished successfully");


        try {
            ManagerService.update(manager);
            logger.info("manager with email {} and id {} has been has bee updated",
                    manager.getEmail(), manager.getId());
        } catch (ServiceException e) {
            String message = e.getMessage();
            if (message.contains("company name"))
                errors.put("company.name", message);
            if (message.contains("email"))
                errors.put("email", message);
            return returnError(request, manager, errors);
        }

        request.getSession().setAttribute("user", manager);
        request.setAttribute("success", "Changes where saved successfully");
        return "manager/profile";
    }

    private String returnError(HttpServletRequest request, Manager manager, Map<String, String> errors) {
        request.setAttribute("errors", errors);
        request.setAttribute("user", manager);
        return "manager/profile";
    }
}

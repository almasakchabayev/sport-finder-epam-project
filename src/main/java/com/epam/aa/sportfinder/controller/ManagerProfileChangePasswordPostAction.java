package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.model.Image;
import com.epam.aa.sportfinder.model.Manager;
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

@ControllerAction(path = "/manager/password",
        httpMethod = HttpMethod.POST,
        accessDeniedTo = {Permission.GUEST})
public class ManagerProfileChangePasswordPostAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(ManagerProfileChangePasswordPostAction.class);

    @Override
    public String execute(HttpServletRequest request) {
        logger.debug("CHanging manager info");
        String currentPassword = request.getParameter("current-password");
        String newPassword = request.getParameter("new-password");
        String confirmNewPassword = request.getParameter("confirm-new-password");

        logger.debug("retrieved data from web form");

        Manager manager = (Manager) request.getSession().getAttribute("user");
        Map<String, String> errors = new HashMap<>();

        if (!manager.getPassword().equals(currentPassword)) {
            errors.put("current-password", "Current password is incorrect");
        }

        if (newPassword.length() < 4) {
            errors.put("password", "Password must be at least 4 characters");
        }

        if (!newPassword.equals(confirmNewPassword)) {
            errors.put("password", "Password and confirm password do not match");
        }

        if (errors.size() > 0)
            return returnError(request, manager, errors);

        manager.setPassword(newPassword);

        try {
            ManagerService.update(manager);
            logger.info("manager with email {} and id {} has changed password",
                    manager.getEmail(), manager.getId());
        } catch (ServiceException e) {
            logger.error("Could not update manager's password for manager with email {}", manager.getEmail());
        }

        request.getSession().setAttribute("user", manager);
        request.setAttribute("success", "Password changed successfully");
        return "manager/profile";
    }

    private String returnError(HttpServletRequest request, Manager manager, Map<String, String> errors) {
        request.setAttribute("errors", errors);
        request.setAttribute("user", manager);
        return "manager/profile";
    }
}

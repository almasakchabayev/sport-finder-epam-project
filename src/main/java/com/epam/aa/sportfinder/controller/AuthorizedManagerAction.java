package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.model.Manager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class AuthorizedManagerAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizedManagerAction.class);

    private boolean isAuthorizedAsManager(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            return false;
        }

        try {
            Manager manager = (Manager) session.getAttribute("user");
        } catch (ClassCastException e) {
            return false;
        }
        return true;
    }

    public String execute(HttpServletRequest request) {
        if (!isAuthorizedAsManager(request)){
            logger.info("Unauthorized access to /manager/*, redirecting to /login");
            // TODO: add error somehow
            return "redirect:/login";
        }
        return executeIfAuthorizedAsManager(request);
    }

    protected abstract String executeIfAuthorizedAsManager(HttpServletRequest request);
}

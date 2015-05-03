package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.model.Manager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

@ControllerAction(path="/manager/submit", method="GET")
public class ManagerSubmitGetAction extends AuthorizedManagerAction {
    private static final Logger logger = LoggerFactory.getLogger(ManagerSubmitGetAction.class);

    @Override
    public String executeIfAuthorizedAsManager(HttpServletRequest request) {
        Manager manager = (Manager) request.getSession().getAttribute("user");
//        request.getParameter("");
        return "manager/submit";
    }
}

package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.model.Manager;
import com.epam.aa.sportfinder.model.SportPlace;
import com.epam.aa.sportfinder.service.SportPlaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAction(path = "/manager/items",
        httpMethod = ControllerAction.HttpMethod.GET,
        accessAllowedTo = {ControllerAction.AuthenticatedAs.MANAGER})
public class ManagerItemsGetAction extends AuthorizedManagerAction {
    private static final Logger logger = LoggerFactory.getLogger(ManagerItemsGetAction.class);

    @Override
    public String executeIfAuthorizedAsManager(HttpServletRequest request) {
        Manager manager = (Manager) request.getSession().getAttribute("user");
        List<SportPlace> sportPlaces = SportPlaceService.findByManager(manager);
        request.setAttribute("sportPlaces", sportPlaces);
        logger.debug("Successfully retrieved sport places for manager {}, opening /manager/items", manager.getEmail());
        return "manager/items";
    }
}
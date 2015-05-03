package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.model.Manager;
import com.epam.aa.sportfinder.model.SportPlace;
import com.epam.aa.sportfinder.service.SportPlaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@ControllerAction(path = "/manager/items", method = "GET")
public class ManagerItemsAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(ManagerItemsAction.class);

    @Override
    public String execute(HttpServletRequest request) throws ControllerException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            logger.info("Unauthorized access to /manager/items, redirecting to /login");
            return "redirect:/login";
        }

        Manager manager;
        try {
            manager = (Manager) session.getAttribute("user");
        } catch (ClassCastException e) {
            logger.info("Unauthorized access to /manager/items, redirecting to /login");
            return "redirect:/login";
        }

        List<SportPlace> sportPlaces = SportPlaceService.findByManager(manager);
        request.setAttribute("sportPlaces", sportPlaces);
        logger.info("SUccessfully retrieved sport places for manager {}, opening /manager/items", manager.getEmail());
        return "manager/items";
    }
}

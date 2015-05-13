package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.dao.SportPlaceDao;
import com.epam.aa.sportfinder.model.FloorCoverage;
import com.epam.aa.sportfinder.model.Manager;
import com.epam.aa.sportfinder.model.Sport;
import com.epam.aa.sportfinder.model.SportPlace;
import com.epam.aa.sportfinder.service.FloorCoverageService;
import com.epam.aa.sportfinder.service.SportPlaceService;
import com.epam.aa.sportfinder.service.SportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.epam.aa.sportfinder.controller.ControllerAction.HttpMethod;
import static com.epam.aa.sportfinder.controller.ControllerAction.Permission;

@ControllerAction(path = "/manager/item/delete",
        httpMethod = HttpMethod.GET,
        accessDeniedTo = {Permission.GUEST, Permission.CUSTOMER})
public class ManagerItemDeleteGetAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(ManagerItemDeleteGetAction.class);

    @Override
    public String execute(HttpServletRequest request) {
        // check if it is an update
        String queryString = request.getQueryString();
        if (queryString != null || queryString.matches("id=\\d+")) {
            Integer id = Integer.valueOf(queryString.substring(queryString.indexOf("=") + 1));
            Manager manager = (Manager) request.getSession().getAttribute("user");
            if (manager.containsSportPlaceId(id)) {
                SportPlaceService.delete(id);
                return "redirect:/manager/items";
            } else {
                request.setAttribute("statusCode", 403);
                return "error";
            }
        }

        request.setAttribute("statusCode", 404);
        return "error";
    }
}

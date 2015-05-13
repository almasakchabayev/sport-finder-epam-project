package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.model.Manager;
import com.epam.aa.sportfinder.model.SportPlace;
import com.epam.aa.sportfinder.service.SportPlaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import static com.epam.aa.sportfinder.controller.ControllerAction.HttpMethod;
import static com.epam.aa.sportfinder.controller.ControllerAction.Permission;

@ControllerAction(path = "/manager/item/undelete",
        httpMethod = HttpMethod.GET,
        accessDeniedTo = {Permission.GUEST, Permission.CUSTOMER})
public class ManagerItemUndeleteGetAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(ManagerItemUndeleteGetAction.class);

    @Override
    public String execute(HttpServletRequest request) {
        // check if it is an update
        String queryString = request.getQueryString();
        if (queryString != null || queryString.matches("id=\\d+")) {
            Integer id = Integer.valueOf(queryString.substring(queryString.indexOf("=") + 1));
            Manager manager = (Manager) request.getSession().getAttribute("user");
            SportPlace sportPlace = SportPlaceService.findById(id);
            if (sportPlace.getManager().getId().equals(manager.getId())) {
                SportPlaceService.undelete(id);
                return "redirect:/manager/deleted";
            } else {
                request.setAttribute("statusCode", "403");
                return "error.jsp";
            }
        }

        request.setAttribute("statusCode", "404");
        return "error.jsp";
    }
}

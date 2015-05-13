package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.model.Manager;
import com.epam.aa.sportfinder.model.SportPlace;
import com.epam.aa.sportfinder.service.SportPlaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.epam.aa.sportfinder.controller.ControllerAction.HttpMethod;
import static com.epam.aa.sportfinder.controller.ControllerAction.Permission;

@ControllerAction(path = "/manager/deleted",
        httpMethod = HttpMethod.GET,
        accessDeniedTo = {Permission.GUEST, Permission.CUSTOMER})
public class ManagerDeletedItemsGetAction implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        Manager manager = (Manager) request.getSession().getAttribute("user");
        List<SportPlace> sportPlaces = SportPlaceService.findDeletedByManager(manager);
        request.setAttribute("sportPlaces", sportPlaces);
        return "manager/deleted";
    }
}

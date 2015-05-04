package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.model.FloorCoverage;
import com.epam.aa.sportfinder.model.Manager;
import com.epam.aa.sportfinder.model.Sport;
import com.epam.aa.sportfinder.service.FloorCoverageService;
import com.epam.aa.sportfinder.service.SportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAction(path="/manager/submit", method="GET")
@ManagerAuthorizedAccess
public class ManagerSubmitGetAction extends AuthorizedManagerAction {
    private static final Logger logger = LoggerFactory.getLogger(ManagerSubmitGetAction.class);

    @Override
    public String executeIfAuthorizedAsManager(HttpServletRequest request) {
        Manager manager = (Manager) request.getSession().getAttribute("user");
        List<FloorCoverage> floorCoverages = FloorCoverageService.findAll();
        request.setAttribute("floorCoverages", floorCoverages);
        List<Sport> sports = SportService.findAll();
        request.setAttribute("sports", sports);
        return "manager/submit";
    }
}

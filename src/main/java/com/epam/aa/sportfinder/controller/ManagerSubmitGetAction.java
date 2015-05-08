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

import static com.epam.aa.sportfinder.controller.ControllerAction.*;

@ControllerAction(path = "/manager/item/submit",
        httpMethod = HttpMethod.GET,
        accessDeniedTo = {Permission.GUEST, Permission.CUSTOMER})
public class ManagerSubmitGetAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(ManagerSubmitGetAction.class);

    @Override
    public String execute(HttpServletRequest request) {
        List<FloorCoverage> floorCoverages = FloorCoverageService.findAll();
        request.setAttribute("floorCoverages", floorCoverages);
        List<Sport> sports = SportService.findAll();
        request.setAttribute("sports", sports);
        return "manager/item/submit";
    }
}

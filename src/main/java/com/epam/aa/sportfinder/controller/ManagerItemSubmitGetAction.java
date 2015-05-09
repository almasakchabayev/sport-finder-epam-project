package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.model.FloorCoverage;
import com.epam.aa.sportfinder.model.Sport;
import com.epam.aa.sportfinder.model.SportPlace;
import com.epam.aa.sportfinder.service.FloorCoverageService;
import com.epam.aa.sportfinder.service.SportPlaceService;
import com.epam.aa.sportfinder.service.SportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.epam.aa.sportfinder.controller.ControllerAction.*;

@ControllerAction(path = "/manager/item/submit",
        httpMethod = HttpMethod.GET,
        accessDeniedTo = {Permission.GUEST, Permission.CUSTOMER})
public class ManagerItemSubmitGetAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(ManagerItemSubmitGetAction.class);

    @Override
    public String execute(HttpServletRequest request) {
        // check if it is an update
        SportPlace sportPlace = null;

        String queryString = request.getQueryString();
        if (queryString == null || !queryString.matches("id=\\d+")) {
            List<FloorCoverage> floorCoverages = FloorCoverageService.findAll();
            request.setAttribute("nonSelectedFloorCoverages", floorCoverages);
            List<Sport> sports = SportService.findAll();
            request.setAttribute("nonSelectedSports", sports);
        } else {
            //prepopulated fields
            FloorCoverage selectedFloorCoverage = null;
            List<Sport> selectedSports;
            List<FloorCoverage> nonSelectedFloorCoverages;
            List<Sport> nonSelectedSports;


            Integer id = Integer.valueOf(queryString.substring(queryString.indexOf("=") + 1));
            sportPlace = SportPlaceService.findById(id);
            if (sportPlace != null) {
                List<FloorCoverage> floorCoveragesToDisplay = FloorCoverageService.findAll();
                nonSelectedFloorCoverages = new ArrayList<>();
                for (FloorCoverage coverage : floorCoveragesToDisplay) {
                    if (coverage.getId().equals(sportPlace.getFloorCoverage().getId())) {
                        selectedFloorCoverage = coverage;
                    } else {
                        nonSelectedFloorCoverages.add(coverage);
                    }
                }
                List<Sport> sportsToDisplay = SportService.findAll();
                selectedSports = new ArrayList<>();
                nonSelectedSports = new ArrayList<>();
                for (Sport sport : sportsToDisplay) {
                    if (sportPlace.getSports().contains(sport)) {
                        selectedSports.add(sport);
                    } else {
                        nonSelectedSports.add(sport);
                    }
                }
                request.setAttribute("item", sportPlace);
                request.setAttribute("nonSelectedFloorCoverages", nonSelectedFloorCoverages);
                request.setAttribute("selectedFloorCoverage", selectedFloorCoverage);
                request.setAttribute("selectedSports", selectedSports);
                request.setAttribute("nonSelectedSports", nonSelectedSports);
            } else {
                throw new ControllerException("could not find sportPlace given id = " + id);
            }
        }


        return "manager/item/submit";
    }
}

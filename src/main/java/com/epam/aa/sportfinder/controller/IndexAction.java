package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.model.Sport;
import com.epam.aa.sportfinder.model.SportPlace;
import com.epam.aa.sportfinder.service.SportPlaceService;
import com.epam.aa.sportfinder.service.SportService;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.epam.aa.sportfinder.controller.ControllerAction.HttpMethod;

@ControllerAction(path = "/index",
        httpMethod = HttpMethod.GET)
public class IndexAction implements Action {
    public String execute(HttpServletRequest request) throws ControllerException {
        List<Sport> sports = SportService.findAll();
        List<SportPlace> sportPlaces = SportPlaceService.findAll();
        request.setAttribute("sportPlaces", sportPlaces);
        request.setAttribute("sports", sports);
        return "index";
    }
}
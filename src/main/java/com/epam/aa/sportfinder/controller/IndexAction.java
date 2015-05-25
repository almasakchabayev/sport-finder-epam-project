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
        int page = 1;
        int recordsPerPage = 4;
        if(request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));

        List<SportPlace> sportPlaces = SportPlaceService.findAllPaginated(page, recordsPerPage);
        Integer numberOfPages = SportPlaceService.getNumberOfPages(recordsPerPage);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("sportPlaces", sportPlaces);

        List<Sport> sports = SportService.findAll();
        request.setAttribute("sports", sports);
        return "index";
    }
}
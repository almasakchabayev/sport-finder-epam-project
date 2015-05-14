package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.model.SportPlace;
import com.epam.aa.sportfinder.service.ServiceException;
import com.epam.aa.sportfinder.service.SportPlaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import static com.epam.aa.sportfinder.controller.ControllerAction.HttpMethod;

@ControllerAction(path = "/item/detail",
        httpMethod = HttpMethod.GET)
public class ItemDetailGetAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(ItemDetailGetAction.class);

    public String execute(HttpServletRequest request) throws ControllerException {
        String queryString = request.getQueryString();
        if (queryString != null || queryString.matches("id=\\d+")) {
            Integer id = Integer.valueOf(queryString.substring(queryString.indexOf("=") + 1));
            SportPlace sportPlace = null;
            try {
                sportPlace = SportPlaceService.findById(id);
            } catch (Exception e) {
                logger.warn("Could not find sport place with id {}", id, e);
            }

            request.setAttribute("item", sportPlace);
            return "item/detail";
        }
        return "redirect:/error";
    }
}
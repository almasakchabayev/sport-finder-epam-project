package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.model.FloorCoverage;
import com.epam.aa.sportfinder.model.Sport;
import com.epam.aa.sportfinder.model.SportPlace;
import com.epam.aa.sportfinder.service.FloorCoverageService;
import com.epam.aa.sportfinder.service.ImageService;
import com.epam.aa.sportfinder.service.SportPlaceService;
import com.epam.aa.sportfinder.service.SportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.epam.aa.sportfinder.controller.ControllerAction.HttpMethod;
import static com.epam.aa.sportfinder.controller.ControllerAction.Permission;

@ControllerAction(path = "/manager/item/image/delete",
        httpMethod = HttpMethod.GET,
        accessDeniedTo = {Permission.GUEST, Permission.CUSTOMER})
public class ManagerItemImageDeleteGetAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(ManagerItemImageDeleteGetAction.class);

    @Override
    public String execute(HttpServletRequest request) {
        // check if it is an update
        SportPlace sportPlace = null;

        String queryString = request.getQueryString();
        if (queryString != null && queryString.matches("id=\\d+")) {
            Integer id = Integer.valueOf(queryString.substring(queryString.indexOf("=") + 1));
            ImageService.delete(id);
            String referer = request.getHeader("Referer");

            String itemId = referer.substring(referer.indexOf("=") + 1);
            return "redirect:/manager/item/submit?id=" + itemId;
        }

        return "redirect:/error";
    }
}

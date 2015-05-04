package com.epam.aa.sportfinder.controller;

import com.epam.aa.sportfinder.model.*;
import com.epam.aa.sportfinder.service.FloorCoverageService;
import com.epam.aa.sportfinder.service.SportPlaceService;
import com.epam.aa.sportfinder.service.SportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ControllerAction(path="/manager/submit", method= "POST")
public class ManagerSubmitPostAction extends AuthorizedManagerAction {
    private static final Logger logger = LoggerFactory.getLogger(ManagerSubmitPostAction.class);

    @Override
    public String executeIfAuthorizedAsManager(HttpServletRequest request) {
        Manager manager = (Manager) request.getSession().getAttribute("user");
        String description = request.getParameter("description");
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String zipcode = request.getParameter("zipcode");
        String addressLine1 = request.getParameter("address-line-1");
        String addressLine2 = request.getParameter("address-line-2");
        String size = request.getParameter("size");
        String floorCoverageName = request.getParameter("floor-coverage");
        String capacity = request.getParameter("capacity");
        String price = request.getParameter("price");
        String[] sports = request.getParameterValues("sport");
        String tribuneCapacity = request.getParameter("tribune-capacity");
        boolean changingRoom = request.getParameter("changing-room")  != null ;
        boolean shower = request.getParameter("shower") != null;
        boolean lightening = request.getParameter("lightening") != null;
        boolean indoor = request.getParameter("indoor") != null;
        String otherInfrastructureFeatures = request.getParameter("other-infrastructure-features");

        SportPlace sportPlace = new SportPlace();
        sportPlace.setDescription(description);
        if (!capacity.equals(""))
            sportPlace.setCapacity(Integer.valueOf(capacity));
        sportPlace.setLightening(lightening);
        sportPlace.setManager(manager);
        sportPlace.setShower(shower);
        sportPlace.setIndoor(indoor);
        sportPlace.setChangingRoom(changingRoom);
        if (!tribuneCapacity.equals("")) {
            sportPlace.setTribuneCapacity(Integer.valueOf(tribuneCapacity));
        } else {
            sportPlace.setTribuneCapacity(0);
        }
        sportPlace.setSize(size);
        sportPlace.setOtherInfrastructureFeatures(otherInfrastructureFeatures);

        if (!price.equals("")) {
            //TODO: when locale is added use this managers locale
            DecimalFormat nf = (DecimalFormat) NumberFormat.getInstance();
            nf.setParseBigDecimal(true);
            BigDecimal bd = (BigDecimal) nf.parse(price, new ParsePosition(0));
            sportPlace.setPricePerHour(bd);
        }

        FloorCoverage floorCoverage = new FloorCoverage();
        floorCoverage.setName(floorCoverageName);
        sportPlace.setFloorCoverage(floorCoverage);

        Address address = new Address();
        address.setCountry(country);
        address.setCity(city);
        address.setAddressLine1(addressLine1);
        address.setAddressLine2(addressLine2);
        address.setZipcode(zipcode);
        sportPlace.setAddress(address);


        Map<String, String> errors = new HashMap<>();
        if (sports != null) {
            for (String sport : sports) {
                Sport s = new Sport();
                s.setName(sport);
                sportPlace.addSport(s);
            }
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<SportPlace>> violations = validator.validate(sportPlace);
        if (violations.size() > 0 ) {
            for (ConstraintViolation<SportPlace> violation : violations) {
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
        }
        if (errors.size() > 0) {
            request.setAttribute("errors", errors);
            request.setAttribute("item", sportPlace);
            logger.debug("errors found during validation, redirecting to manager/submit.jsp");
            //TODO: load these to context once and for all
            List<FloorCoverage> floorCoverages = FloorCoverageService.findAll();
            request.setAttribute("floorCoverages", floorCoverages);
            List<Sport> sportsToDisplay = SportService.findAll();
            request.setAttribute("sports", sportsToDisplay);
            return "manager/submit";
        }

        SportPlaceService.create(sportPlace);
        return "redirect:/manager/items";
    }
}

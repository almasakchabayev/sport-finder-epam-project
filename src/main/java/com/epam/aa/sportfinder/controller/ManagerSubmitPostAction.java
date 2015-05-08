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
import java.util.*;

import static com.epam.aa.sportfinder.controller.ControllerAction.*;

@ControllerAction(path = "/manager/item/submit",
        httpMethod = HttpMethod.POST,
        accessDeniedTo = {Permission.GUEST, Permission.CUSTOMER})
public class ManagerSubmitPostAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(ManagerSubmitPostAction.class);

    @Override
    public String execute(HttpServletRequest request) {
        Manager manager = (Manager) request.getSession().getAttribute("user");
        String description = request.getParameter("description");
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String zipcode = request.getParameter("zipcode");
        String addressLine1 = request.getParameter("address-line-1");
        String addressLine2 = request.getParameter("address-line-2");
        String size = request.getParameter("size");
        String floorCoverageId = request.getParameter("floor-coverage");
        String capacity = request.getParameter("capacity");
        String price = request.getParameter("price");
        String[] sportIds = request.getParameterValues("sport");
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
        floorCoverage.setId(Integer.valueOf(floorCoverageId));
        sportPlace.setFloorCoverage(floorCoverage);

        Address address = new Address();
        address.setCountry(country);
        address.setCity(city);
        address.setAddressLine1(addressLine1);
        address.setAddressLine2(addressLine2);
        address.setZipcode(zipcode);
        sportPlace.setAddress(address);


        Map<String, String> errors = new HashMap<>();
        if (sportIds != null) {
            for (String sportId : sportIds) {
                Sport s = new Sport();
                s.setId(Integer.valueOf(sportId));
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
            List<FloorCoverage> floorCoveragesToDisplay = FloorCoverageService.findAll();
            FloorCoverage selectedFloorCoverage = null;
            List<FloorCoverage> nonSelectedFloorCoverages = new ArrayList<>();
            for (FloorCoverage coverage : floorCoveragesToDisplay) {
                if (coverage.getId().toString().equals(floorCoverageId)) {
                    selectedFloorCoverage = coverage;
                } else {
                    nonSelectedFloorCoverages.add(coverage);
                }
            }

            List<Sport> sportsToDisplay = SportService.findAll();
            List<String> selectedSportIds = Arrays.asList(sportIds);
            List<Sport> selectedSports = new ArrayList<>();
            List<Sport> nonSelectedSports = new ArrayList<>();
            for (Sport sport : sportsToDisplay) {
                if(selectedSportIds.contains(sport.getId().toString())) {
                    selectedSports.add(sport);
                } else {
                    nonSelectedSports.add(sport);
                }
            }

            request.setAttribute("nonSelectedFloorCoverages", nonSelectedFloorCoverages);
            request.setAttribute("selectedFloorCoverage", selectedFloorCoverage);
            request.setAttribute("selectedSports", selectedSports);
            request.setAttribute("nonSelectedSports", nonSelectedSports);
            return "manager/item/submit";
        }

        SportPlaceService.create(sportPlace);
        return "redirect:/manager/items";
    }
}

package com.epam.aa.sportfinder.service;

import com.epam.aa.sportfinder.dao.*;
import com.epam.aa.sportfinder.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SportPlaceService extends BaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SportPlaceService.class);

    public static SportPlace create(SportPlace sportPlace) {


        DaoManager daoManager = createDaoManager();

        return daoManager.executeTx(manager -> {
            SportPlaceDao sportPlaceDao = manager.getDao(SportPlace.class);
            FloorCoverageDao floorCoverageDao = manager.getDao(FloorCoverage.class);
            SportDao sportDao = manager.getDao(Sport.class);
            AddressDao addressDao = manager.getDao(Address.class);
            // no need for manager as manager was extracted previously
            SportPlace result = null;

            Address address = addressDao.insert(sportPlace.getAddress());
            sportPlace.setAddress(address);

//            FloorCoverage floorCoverage = floorCoverageDao.findByName(sportPlace.getFloorCoverage());
//            sportPlace.setFloorCoverage(floorCoverage);
//
//            List<Sport> sports = new ArrayList<>();
//            // todo change names to ids
//            for (Sport sport : sportPlace.getSports()) {
//                Sport fullSport = sportDao.findByName(sport);
//                sports.add(fullSport);
//            }
//            sportPlace.setSports(sports);

            result = sportPlaceDao.insert(sportPlace);
            result = sportPlaceDao.insertCorrespondingSports(sportPlace);
            return result;
        });
    }

    public static SportPlace findById(Integer id) {
        DaoManager daoManager = createDaoManager();

        return daoManager.executeTx(manager -> {
            SportPlaceDao sportPlaceDao = manager.getDao(SportPlace.class);
            SportPlace sportPlace = sportPlaceDao.findById(id);

            retrieveRelatedEntities(manager, sportPlaceDao, sportPlace);
            return sportPlace;
        });
    }

    public static List<SportPlace> findByManager(Manager manager) {
        DaoManager daoManager = createDaoManager();

        return daoManager.executeTx(daoMng -> {
            SportPlaceDao sportPlaceDao = daoMng.getDao(SportPlace.class);
            List<SportPlace> sportPlaces = sportPlaceDao.findByManager(manager);

            for (SportPlace sportPlace : sportPlaces) {
                retrieveRelatedEntities(daoMng, sportPlaceDao, sportPlace);
            }
            return sportPlaces;
        });
    }

    private static void retrieveRelatedEntities(DaoManager manager, SportPlaceDao sportPlaceDao, SportPlace sportPlace) {
        FloorCoverageDao floorCoverageDao = manager.getDao(FloorCoverage.class);
        FloorCoverage floorCoverage = floorCoverageDao.findById(sportPlace.getFloorCoverage().getId());
        sportPlace.setFloorCoverage(floorCoverage);

        AddressDao addressDao = manager.getDao(Address.class);
        Address address = addressDao.findById(sportPlace.getAddress().getId());
        sportPlace.setAddress(address);

        List<Integer> correspondingSportIds = sportPlaceDao.findCorrespondingSportIds(sportPlace);
        SportDao sportDao = manager.getDao(Sport.class);
        for (Integer correspondingSportId : correspondingSportIds) {
            Sport sport = sportDao.findById(correspondingSportId);
            sportPlace.addSport(sport);
        }
    }
}

package com.epam.aa.sportfinder.service;

import com.epam.aa.sportfinder.dao.*;
import com.epam.aa.sportfinder.model.*;

import java.util.List;

public class SportPlaceService extends BaseService {
    public static SportPlace create(SportPlace sportPlace) {
        DaoManager daoManager = createDaoManager();

        return daoManager.executeTx(manager -> {
            SportPlaceDao dao = manager.getDao(SportPlace.class);
            SportPlace sportplace = dao.insert(sportPlace);
            sportplace = dao.insertCorrespondingSports(sportPlace);
            return sportplace;
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

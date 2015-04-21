package com.epam.aa.sportfinder.service;

import com.epam.aa.sportfinder.dao.*;
import com.epam.aa.sportfinder.model.Address;
import com.epam.aa.sportfinder.model.FloorCoverage;
import com.epam.aa.sportfinder.model.SportPlace;

public class SportPlaceService {
    public static SportPlace create(SportPlace sportPlace) {
        DaoFactory daoFactory = DaoFactory.getInstance();
        DaoManager daoManager = daoFactory.createDaoManager();

        return daoManager.executeTx(manager -> {
            SportPlaceDao dao = manager.getDao(SportPlace.class);
            SportPlace sportplace = dao.insert(sportPlace);
            sportplace = dao.insertCorrespondingSports(sportPlace);
            return sportplace;
        });
    }

    public static SportPlace findById(Integer id) {
        DaoFactory daoFactory = DaoFactory.getInstance();
        DaoManager daoManager = daoFactory.createDaoManager();

        return daoManager.executeTx(manager -> {
            SportPlaceDao sportPlaceDao = manager.getDao(SportPlace.class);
            SportPlace sportPlace = sportPlaceDao.findById(id);

            FloorCoverageDao floorCoverageDao = manager.getDao(FloorCoverage.class);
            FloorCoverage floorCoverage = floorCoverageDao.findById(sportPlace.getFloorCoverage().getId());
            sportPlace.setFloorCoverage(floorCoverage);

            AddressDao addressDao = manager.getDao(Address.class);
            Address address = addressDao.findById(sportPlace.getAddress().getId());
            sportPlace.setAddress(address);

            sportPlaceDao.findCorrespondingSportIds(sportPlace);
            return sportPlace;
        });
    }
}

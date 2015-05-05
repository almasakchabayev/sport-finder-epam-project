package com.epam.aa.sportfinder.service;

import com.epam.aa.sportfinder.dao.DaoManager;
import com.epam.aa.sportfinder.dao.FloorCoverageDao;
import com.epam.aa.sportfinder.model.FloorCoverage;

import java.util.List;

public class FloorCoverageService extends BaseService {

    public static List<FloorCoverage> findAll() {
        DaoManager daoManager = createDaoManager();
        return daoManager.executeTx(manager -> {
            FloorCoverageDao floorCoverageDao = manager.getDao(FloorCoverage.class);
            return floorCoverageDao.findAllNonDeleted();
        });
    }
}

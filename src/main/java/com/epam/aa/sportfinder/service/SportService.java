package com.epam.aa.sportfinder.service;

import com.epam.aa.sportfinder.dao.DaoManager;
import com.epam.aa.sportfinder.dao.SportDao;
import com.epam.aa.sportfinder.model.Sport;

import java.util.List;

public class SportService extends BaseService {

    public static List<Sport> findAll() {
        DaoManager daoManager = createDaoManager();
        return daoManager.executeTx(manager -> {
            SportDao sportDao = manager.getDao(Sport.class);
            return sportDao.findAllNonDeleted();
        });
    }
}

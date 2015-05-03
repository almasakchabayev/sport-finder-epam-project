package com.epam.aa.sportfinder.service;

import com.epam.aa.sportfinder.dao.DaoFactory;
import com.epam.aa.sportfinder.dao.DaoManager;

public abstract class BaseService {

    protected static DaoManager createDaoManager() {
        DaoFactory daoFactory = DaoFactory.getInstance();
        return daoFactory.createDaoManager();
    }
}

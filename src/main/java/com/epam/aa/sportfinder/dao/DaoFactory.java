package com.epam.aa.sportfinder.dao;

import com.epam.aa.sportfinder.config.AppConfig;
import com.epam.aa.sportfinder.dao.jdbc.JdbcDaoFactory;

public abstract class DaoFactory {

    public static DaoFactory getInstance() {
        if (AppConfig.daoFactoryImpl.equals("jdbc")) {
            return new JdbcDaoFactory();
        }
        throw new DaoException("Implementation of DaoFactory was not found");
    }
    public abstract DaoManager createDaoManager();
}

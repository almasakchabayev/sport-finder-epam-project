package com.epam.aa.sportfinder.dao;

import com.epam.aa.sportfinder.config.AppProperties;
import com.epam.aa.sportfinder.dao.jdbc.JdbcDaoFactory;

public abstract class DaoFactory {

    public static DaoFactory getInstance() {
        if (AppProperties.getDaoProperty("dao.factory").equals("jdbc")) {
            return new JdbcDaoFactory();
        }
        throw new DaoException("Implementation of DaoFactory was not found");
    }
    public abstract DaoManager createDaoManager();
}

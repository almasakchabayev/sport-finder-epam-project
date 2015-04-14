package com.epam.aa.sportfinder.dao;

import com.epam.aa.sportfinder.dao.jdbc.JdbcDaoFactory;

public abstract class DaoFactory {
    private static String impl;

    public static DaoFactory getInstance() {
        if (impl.equals("jdbc")) {
            return new JdbcDaoFactory();
        }
        throw new DaoException("Implementation of DaoFactory was not found");
    }

    public static void setImpl(String impl) {
        DaoFactory.impl = impl;
    }

    public abstract DaoManager createDaoManager();
}

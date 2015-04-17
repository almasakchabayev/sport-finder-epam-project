package com.epam.aa.sportfinder.dao;

import com.epam.aa.sportfinder.dao.jdbc.JdbcDaoFactory;

public abstract class DaoFactory {
    private static Type defaultType;

    public static DaoFactory getInstance() {
        return getInstance(defaultType);
    }

    public static DaoFactory getInstance(Type type) {
        if (type == null)
            throw new DaoException("Implementation was not specified");
        if (type.equals(Type.JDBC))
            return JdbcDaoFactory.getInstance();
        else
            throw new DaoException("Implementation for this type does not exist");
    }

    public static void setDefaultType(String defaultType) {
        if (defaultType.equals("jdbc"))
            DaoFactory.defaultType = Type.JDBC;
        else
            throw new DaoException("Such implementation of DaoFactory does not exist");
    }

    public abstract DaoManager createDaoManager();

    public enum Type {
        JDBC, XML, MONGO
    }
}

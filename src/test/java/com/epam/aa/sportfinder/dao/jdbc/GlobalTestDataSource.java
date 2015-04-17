package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoFactory;
import com.epam.aa.sportfinder.dao.DaoManager;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

public abstract class GlobalTestDataSource {
    public static final DaoFactory DAO_FACTORY = DaoFactory.getInstance(DaoFactory.Type.JDBC);

    public static DataSource getDataSource() {
        JdbcDaoFactory daoFactory = (JdbcDaoFactory) DAO_FACTORY;
        return daoFactory.getDataSource();
    }

    public static DaoManager getDaoManager() {
        return DAO_FACTORY.createDaoManager();
    }
}

package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoFactory;
import com.epam.aa.sportfinder.dao.DaoManager;

import javax.sql.DataSource;

public class JdbcDaoFactory extends DaoFactory {
    private static DataSource dataSource;

    public static void setDataSource(DataSource dataSource) {
        JdbcDaoFactory.dataSource = dataSource;
    }

    @Override
    public DaoManager createDaoManager() {
        return new JdbcDaoManager();
    }
}

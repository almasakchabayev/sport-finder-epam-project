package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.GenericDao;
import com.epam.aa.sportfinder.model.BaseEntity;

import java.sql.Connection;

public abstract class JdbcBaseDao<T extends BaseEntity> {
    private Connection connection;

    public JdbcBaseDao(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
}

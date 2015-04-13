package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.GenericDao;
import com.epam.aa.sportfinder.model.BaseEntity;

import java.sql.Connection;

public abstract class JdbcBaseDao<T extends BaseEntity> implements GenericDao<T> {
    private Connection connection;

    public JdbcBaseDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public T create(T entity) throws DaoException {
        return null;
    }

    @Override
    public void update(T entity) throws DaoException {

    }

    @Override
    public void delete(T entity) throws DaoException {

    }

    public Connection getConnection() {
        return connection;
    }
}

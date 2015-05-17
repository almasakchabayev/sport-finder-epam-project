package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.*;
import com.epam.aa.sportfinder.model.BaseEntity;
import com.epam.aa.sportfinder.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcDaoManager implements DaoManager {
    private static final Logger logger = LoggerFactory.getLogger(JdbcDaoManager.class);

    private final Connection connection;

    public JdbcDaoManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T> T execute(DaoCommand<T> daoCommand) throws DaoException {
        try {
            T result = daoCommand.execute(this);
            return result;
        } finally {
            try {
                connection.close();
                logger.debug("Connection closed, executed properly");
            } catch (SQLException e) {
                throw new DaoException("Connection did not close properly when executing daoCommand", e);
            }
        }
    }

    @Override
    public <T> T transaction(DaoCommand<T> daoCommand) throws DaoException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException("Could not set autocommit to false for connection " + connection, e);
        }
        try {
            T result = daoCommand.execute(this);
            connection.commit();
            return result;
        } catch (SQLException | ServiceException e) {
            try {
                connection.rollback();
                //TODO: add more meaningfull log
                logger.error("Transaction has been rolled back, when processing daoCommand {}", daoCommand);
            } catch (SQLException e1) {
                throw new DaoException("Could not rollback transaction", e1);
            }
            throw new DaoException("Transaction failed", e);
        } finally {
            try {
                connection.setAutoCommit(true);
                logger.debug("transaction executed properly");
            } catch (SQLException e) {
                throw new DaoException("Autocommit could not set to true when executing daoCommand", e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    //TODO: create a Map and store Daos
    public <T extends GenericDao> T getDao(Class<? extends BaseEntity> clazz) throws DaoException {
        String entityClassName = clazz.getSimpleName();
        String jdbcPackageName = this.getClass().getPackage().getName();
        String daoClassName = jdbcPackageName + ".Jdbc" + entityClassName + "Dao";

        try {
            Class<?> daoClass = Class.forName(daoClassName);
            Constructor<?> constructor = daoClass.getConstructor(Connection.class);
            T instance = (T) constructor.newInstance(connection);
            return instance;
        } catch (ClassNotFoundException e) {
            throw new DaoException("There is no JdbcDao matching " + entityClassName, e);
        } catch (NoSuchMethodException e) {
            throw new DaoException("Dao class corresponding to " +
                    entityClassName + " does not have constructor that accepts connection", e);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new DaoException("Could not instantiate dao for " + entityClassName, e);
        }
    }
}

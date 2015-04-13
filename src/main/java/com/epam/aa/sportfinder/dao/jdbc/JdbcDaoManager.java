package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoCommand;
import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.DaoManager;
import com.epam.aa.sportfinder.dao.AddressDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcDaoManager implements DaoManager {
    private static final Logger logger = LoggerFactory.getLogger(JdbcDaoManager.class);

    private Connection connection;
    private AddressDao addressDao;

    public JdbcDaoManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T> T execute(DaoCommand<T> daoCommand) {
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
    public <T> T transaction(DaoCommand<T> daoCommand) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException("Could not set autocommit to false for connection " + connection, e);
        }
        try {
            T result = daoCommand.execute(this);
            connection.commit();
            return result;
        }  catch (SQLException e) {
            try {
                connection.rollback();
                //TODO: add more meaningfull log
                logger.warn("Connection has been rolled back, when processing daoCommand");
            } catch (SQLException e1) {
                throw new DaoException("Could not commit rollback transaction", e1);
            }
            throw new DaoException("Could not commit daoCommand", e);
        } finally {
            //TODO: how can I not exception here? as it can override exception in catch block
            try {
                connection.setAutoCommit(true);
                logger.debug("transaction executed properly");
            } catch (SQLException e) {
                throw new DaoException("Autocommit could not set to true when executing daoCommand", e);
            }
        }
    }

    @Override
    public AddressDao getAddressDao() {
        if (addressDao == null) {
            return new JdbcAddressDao(connection);
        }
        return addressDao;
    }
}

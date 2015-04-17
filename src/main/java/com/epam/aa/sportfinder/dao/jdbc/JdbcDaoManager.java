package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcDaoManager implements DaoManager {
    private static final Logger logger = LoggerFactory.getLogger(JdbcDaoManager.class);

    //TODO: final
    private final Connection connection;
    private AddressDao addressDao;
    private FloorCoverageDao floorCoverageDao;
    private SportDao sportDao;

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

    @Override
    public AddressDao getAddressDao() {
        if (addressDao == null) {
            addressDao = new JdbcAddressDao(connection);
        }
        return addressDao;
    }

    @Override
    public FloorCoverageDao getFloorCoverageDao() {
        if (floorCoverageDao == null) {
            floorCoverageDao = new JdbcFloorCoverageDao(connection);
        }
        return floorCoverageDao;
    }

    @Override
    public SportDao getSportDao() {
        if (sportDao == null)
            sportDao = new JdbcSportDao(connection);
        return sportDao;
    }
}

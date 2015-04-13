package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.SportPlaceDao;
import com.epam.aa.sportfinder.model.SportPlace;

import java.sql.Connection;

public class JdbcSportPlaceDao extends JdbcBaseDao<SportPlace> implements SportPlaceDao{

    public JdbcSportPlaceDao(Connection connection) {
        super(connection);
    }

    @Override
    public void find(SportPlace entity) throws DaoException {

    }
}

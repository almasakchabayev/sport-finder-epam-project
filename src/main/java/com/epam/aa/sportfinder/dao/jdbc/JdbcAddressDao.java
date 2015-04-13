package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.AddressDao;
import com.epam.aa.sportfinder.model.SportPlace;

import java.sql.Connection;

public class JdbcAddressDao extends JdbcBaseDao<SportPlace> implements AddressDao {

    public JdbcAddressDao(Connection connection) {
        super(connection);
    }

    @Override
    public void find(SportPlace entity) throws DaoException {

    }
}

package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.SportPlaceDao;
import com.epam.aa.sportfinder.model.SportPlace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class JdbcSportPlaceDao extends JdbcBaseDao<SportPlace> implements SportPlaceDao {
    public JdbcSportPlaceDao(Connection connection) {
        super(connection);
    }

//    public SportPlace insertAddress(SportPlace sportPlace) {
//        if (sportPlace.getAddress() == null)
//            throw new DaoException("Insertion failure, address is null");
//
//    }
}

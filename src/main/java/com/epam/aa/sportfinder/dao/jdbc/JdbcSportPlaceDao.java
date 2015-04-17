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
    private static final Logger logger = LoggerFactory.getLogger(JdbcSportPlaceDao.class);

    private static final String SQL_FIND_BY_ID =
            "SELECT id, uuid, deleted, size, capacity, addressLine1, addressLine2, zipcode " +
                    "FROM Address WHERE id = ?";

    public JdbcSportPlaceDao(Connection connection) {
        super(connection);
    }

    @Override
    public SportPlace findById(Integer id) {
        SportPlace sportPlace;
        if (id == null) throw new DaoException(new NullPointerException("Could not find element by id, id is null"));
        if (id < 1) throw new DaoException(new IllegalArgumentException("Could not find element by id, id cannot be less than 1"));
        try (PreparedStatement pst = getConnection().prepareStatement(SQL_FIND_BY_ID)){
            pst.setObject(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) sportPlace = map(rs);
                else throw new DaoException("Could not find element by id " + id);
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find element by id " + id, e);
        }
        return sportPlace;
    }

    private static SportPlace map(ResultSet resultSet) throws SQLException {
        SportPlace sportPlace = new SportPlace();
        sportPlace.setId(resultSet.getInt("id"));
        sportPlace.setUuid((UUID) resultSet.getObject("uuid"));
        sportPlace.setDeleted(resultSet.getBoolean("deleted"));
//        sportPlace.setCountry(resultSet.getString("country"));
//        sportPlace.setCity(resultSet.getString("city"));
//        sportPlace.setAddressLine1(resultSet.getString("addressLine1"));
//        sportPlace.setAddressLine2(resultSet.getString("addressLine2"));
//        sportPlace.setZipcode(resultSet.getString("zipcode"));
        return sportPlace;
    }
}

package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.AddressDao;
import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.model.Address;
import com.epam.aa.sportfinder.model.SportPlace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.UUID;

public class JdbcAddressDao extends JdbcBaseDao<Address> implements AddressDao {
    private static final Logger logger = LoggerFactory.getLogger(JdbcAddressDao.class);

    private static final String SQL_FIND_BY_ID =
            "SELECT id, uuid, deleted, country, city, addressLine1, addressLine2, zipcode " +
                    "FROM Address WHERE id = ?";

    public JdbcAddressDao(Connection connection) {
        super(connection);
    }

    @Override
    public Address findById(Integer id) {
        Address address;
        if (id == null) throw new DaoException(new NullPointerException("Could not find element by id, id is null"));
        if (id < 1) throw new DaoException(new IllegalArgumentException("Could not find element by id, id cannot be less than 1"));
        try (PreparedStatement pst = getConnection().prepareStatement(SQL_FIND_BY_ID)){
            pst.setObject(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) address = map(rs);
                else throw new DaoException("Could not find element by id " + id);
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find element by id " + id, e);
        }
        return address;
    }

    private static Address map(ResultSet resultSet) throws SQLException {
        Address address = new Address();
        address.setId(resultSet.getInt("id"));
        address.setUuid((UUID) resultSet.getObject("uuid"));
        address.setDeleted(resultSet.getBoolean("deleted"));
        address.setCountry(resultSet.getString("country"));
        address.setCity(resultSet.getString("city"));
        address.setAddressLine1(resultSet.getString("addressLine1"));
        address.setAddressLine2(resultSet.getString("addressLine2"));
        address.setZipcode(resultSet.getString("zipcode"));
        return address;
    }
}

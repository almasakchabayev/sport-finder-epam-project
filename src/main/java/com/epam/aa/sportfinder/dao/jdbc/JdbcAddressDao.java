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
    public static final String SQL_INSERT =
            "INSERT INTO Address(" +
                    "uuid, country, city, addressLine1, addressLine2, zipcode) " +
                    "VALUES(?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE Address SET country = ?, city = ?, " +
                    "addressLine1 = ?, addressLine2 = ?, zipcode = ?  WHERE id = ?";
    private static final String SQL_DELETE =
            "UPDATE Address SET deleted = FALSE WHERE id = ?";


    public JdbcAddressDao(Connection connection) {
        super(connection);
    }

    @Override
    public Address find(Integer id) {
        Address address = null;
        try (PreparedStatement pst = getConnection().prepareStatement(SQL_FIND_BY_ID)){
            pst.setObject(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    address = map(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find element by id", e);
        }
        return address;
    }

    @Override
    public void update(Address address) {
        if (address.getId() == null) {
            throw new DaoException(new IllegalArgumentException(
                    "Address is not created yet, the address ID is null."));
        }

        try (PreparedStatement pst = getConnection().prepareStatement(SQL_UPDATE)){
            pst.setString(1, address.getCountry());
            pst.setString(2, address.getCity());
            pst.setString(3, address.getAddressLine1());
            pst.setString(4, address.getAddressLine2());
            pst.setString(5, address.getZipcode());
            ///TODO: understand what to di with UUID
            pst.setInt(6, address.getId());

            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                //TODO: log warn
                throw new DaoException("Updating address failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DaoException("Updating address failed", e);
        }
    }

    @Override
    public void delete(Address address) {
        if (address.getId() == null) {
            throw new DaoException(new IllegalArgumentException(
                    "Address without id cannot be deleted"));
        }

        try (PreparedStatement pst = getConnection().prepareStatement(SQL_DELETE)){
            pst.setInt(1, address.getId());

            pst.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Deleting address failed", e);
        }
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

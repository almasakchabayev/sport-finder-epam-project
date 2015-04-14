package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.AddressDao;
import com.epam.aa.sportfinder.model.Address;
import com.epam.aa.sportfinder.model.SportPlace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.UUID;

public class JdbcAddressDao extends JdbcBaseDao<SportPlace> implements AddressDao {
    private static final Logger logger = LoggerFactory.getLogger(JdbcAddressDao.class);

    private static final String SQL_FIND_BY_ID =
            "SELECT * FROM Address WHERE id = ?";
    public static final String SQL_INSERT = "INSERT INTO Address(" +
            "uuid, country, city, addressLine1, addressLine2, zipcode) " +
            "VALUES(?, ?, ?, ?, ?, ?);";


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
    public void create(Address address) {
        try (PreparedStatement pst = getConnection().prepareStatement(SQL_INSERT)){
            pst.setObject(1, address.getUuid());
            pst.setString(2, address.getCountry());
            pst.setString(3, address.getCity());
            pst.setString(4, address.getAddressLine1());
            pst.setString(5, address.getAddressLine2());
            pst.setString(6, address.getZipcode());

            pst.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to save address to database", e);
        }
    }

    @Override
    public void update(Address entity) {

    }

    @Override
    public void delete(Address entity) {

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

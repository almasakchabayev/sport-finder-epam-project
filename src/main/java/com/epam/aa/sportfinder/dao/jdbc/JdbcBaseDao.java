package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.GenericDao;
import com.epam.aa.sportfinder.model.BaseEntity;

import java.sql.*;

public abstract class JdbcBaseDao<T extends BaseEntity> implements GenericDao<T> {
    private Connection connection;

    public JdbcBaseDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public T create(T entity) throws DaoException {
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String stm = "INSERT INTO address(uuid, country, city, address_line_1, address_line_2, zipcode) " +
                    "VALUES(?, ?, ?) RETURNING id;";
            pst = connection.prepareStatement(stm);
            pst.setString(1, entity.getFirstName());
            pst.setString(2, customer.getLastName());
            //TODO: relationship to contactinfo
//            if (customer.getContactInfo() != null) {
//                ContactInfoDAO contactInfoDAO = new ContactInfoDAO();
//                contactInfoDAO.insertWithReturningId(customer.getContactInfo());
//            }
            //TODO: what to do about password?
            Date birthDateToStore = Util.getSQLDateFromLocalDate(customer.getBirthDate());
            pst.setDate(3, birthDateToStore);

            rs = pst.executeQuery();
        } catch (SQLException e) {
            //TODO: rethrow
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                //TODO: rethrow
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void update(T entity) throws DaoException {

    }

    @Override
    public void delete(T entity) throws DaoException {

    }

    public Connection getConnection() {
        return connection;
    }
}

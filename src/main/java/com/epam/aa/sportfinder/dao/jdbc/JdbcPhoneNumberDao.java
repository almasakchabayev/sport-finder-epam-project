package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.PhoneNumberDao;
import com.epam.aa.sportfinder.model.PhoneNumber;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcPhoneNumberDao extends JdbcBaseDao<PhoneNumber> implements PhoneNumberDao {
    public JdbcPhoneNumberDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<PhoneNumber> findByIds(List<Integer> phoneNumberIds) throws DaoException {
        if (phoneNumberIds == null)
            throw new DaoException("Could not find phoneNumbers, list is null");

        StringBuffer sqlBuffer = new StringBuffer("SELECT id, uuid, deleted, number FROM PhoneNumber " +
                "WHERE deleted = FALSE AND");
        String prefix = "";
        for (Integer phoneNumberId : phoneNumberIds) {
            sqlBuffer.append(prefix);
            prefix = " AND";
            sqlBuffer.append(" id = " + phoneNumberId);
        }

        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        try (Statement st = getConnection().createStatement()) {
            try (ResultSet rs = st.executeQuery(sqlBuffer.toString())) {
                while (rs.next()) {
                    PhoneNumber phoneNumber = new PhoneNumber();
                    phoneNumber.setId(rs.getInt("id"));
                    phoneNumber.setUuid((UUID) rs.getObject("uuid"));
                    phoneNumber.setDeleted(rs.getBoolean("deleted"));
                    phoneNumber.setNumber(rs.getString("number"));
                    phoneNumbers.add(phoneNumber);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot find corresponding phone Numbers", e);
        }
        return phoneNumbers;
    }
}

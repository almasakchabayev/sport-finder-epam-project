package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.PhoneNumberDao;
import com.epam.aa.sportfinder.model.PhoneNumber;

import java.sql.Connection;

public class JdbcPhoneNumberDao extends JdbcBaseDao<PhoneNumber> implements PhoneNumberDao {
    public JdbcPhoneNumberDao(Connection connection) {
        super(connection);
    }
}

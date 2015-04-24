package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.CompanyDao;
import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.model.Company;
import com.epam.aa.sportfinder.model.PhoneNumber;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcCompanyDao extends JdbcBaseDao<Company> implements CompanyDao {
    public JdbcCompanyDao(Connection connection) {
        super(connection);
    }

}

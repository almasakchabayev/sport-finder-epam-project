package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.FloorCoverageDao;
import com.epam.aa.sportfinder.model.FloorCoverage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class JdbcFloorCoverageDao extends JdbcBaseDao<FloorCoverage> implements FloorCoverageDao {
    public JdbcFloorCoverageDao(Connection connection) {
        super(connection);
    }
}

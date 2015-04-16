package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.AddressDao;
import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.FloorCoverageDao;
import com.epam.aa.sportfinder.model.Address;
import com.epam.aa.sportfinder.model.FloorCoverage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class JdbcFloorCoverageDao extends JdbcBaseDao<FloorCoverage> implements FloorCoverageDao {
    private static final Logger logger = LoggerFactory.getLogger(JdbcFloorCoverageDao.class);

    private static final String SQL_FIND_BY_ID =
            "SELECT id, uuid, deleted, name " +
                    "FROM FloorCoverage WHERE id = ?";

    public JdbcFloorCoverageDao(Connection connection) {
        super(connection);
    }

    @Override
    public FloorCoverage findById(Integer id) {
        FloorCoverage floorCoverage;
        if (id == null) throw new DaoException(new NullPointerException("Could not find element by id, id is null"));
        if (id < 1) throw new DaoException(new IllegalArgumentException("Could not find element by id, id cannot be less than 1"));
        try (PreparedStatement pst = getConnection().prepareStatement(SQL_FIND_BY_ID)){
            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) floorCoverage = map(rs);
                else throw new DaoException("Could not find element by id " + id);
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find element by id " + id, e);
        }
        return floorCoverage;
    }

    private static FloorCoverage map(ResultSet resultSet) throws SQLException {
        FloorCoverage floorCoverage = new FloorCoverage();
        floorCoverage.setId(resultSet.getInt("id"));
        floorCoverage.setUuid((UUID) resultSet.getObject("uuid"));
        floorCoverage.setDeleted(resultSet.getBoolean("deleted"));
        floorCoverage.setName(resultSet.getString("name"));
        return floorCoverage;
    }
}

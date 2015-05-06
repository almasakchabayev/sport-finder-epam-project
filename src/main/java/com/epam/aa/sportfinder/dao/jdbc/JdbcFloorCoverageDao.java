package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.FloorCoverageDao;
import com.epam.aa.sportfinder.model.FloorCoverage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcFloorCoverageDao extends JdbcBaseDao<FloorCoverage> implements FloorCoverageDao {
    public JdbcFloorCoverageDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<FloorCoverage> findAll() {
        String sql = "SELECT id, uuid, deleted, name " +
                "FROM FloorCoverage WHERE deleted = FALSE";

        List<FloorCoverage> floorCoverages = new ArrayList<>();
        try (Statement st = getConnection().createStatement()) {
            try (ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    FloorCoverage floorCoverage = getFloorCoverageFromResultSet(rs);
                    floorCoverages.add(floorCoverage);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Could not fetch all Floor Coverages", e);
        }
        return floorCoverages;
    }

    @Override
    public FloorCoverage findByName(FloorCoverage floorCoverage) {
        String name = floorCoverage.getName();
        if (name == null) {
            throw new DaoException("Cannot find floorCoverage by name if name is null");
        }

        String sql = "SELECT id, uuid, deleted, name " +
                "FROM FloorCoverage WHERE deleted = FALSE AND name = " + "'" + name + "'";

        try (Statement st = getConnection().createStatement()) {
            try (ResultSet rs = st.executeQuery(sql)) {
                if (rs.next()) {
                    floorCoverage = getFloorCoverageFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Could not fetch Floor Coverage by name " + name, e);
        }
        return floorCoverage;
    }

    private FloorCoverage getFloorCoverageFromResultSet(ResultSet resultSet) throws SQLException {
        FloorCoverage floorCoverage = new FloorCoverage();
        floorCoverage.setId(resultSet.getInt("id"));
        floorCoverage.setUuid((UUID) resultSet.getObject("uuid"));
        floorCoverage.setDeleted(resultSet.getBoolean("deleted"));
        floorCoverage.setName(resultSet.getString("name"));
        return floorCoverage;
    }
}

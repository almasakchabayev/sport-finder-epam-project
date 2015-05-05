package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.SportDao;
import com.epam.aa.sportfinder.model.Sport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcSportDao extends JdbcBaseDao<Sport> implements SportDao {
    public JdbcSportDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Sport> findAllNonDeleted() throws DaoException {
        String sql = "SELECT id, uuid, deleted, name " +
                "FROM Sport WHERE deleted = FALSE";

        List<Sport> sports = new ArrayList<>();
        try (Statement st = getConnection().createStatement()) {
            try (ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    Sport sport = getSportFromResultSet(rs);
                    sports.add(sport);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Could not fetch all Sports", e);
        }
        return sports;
    }

    @Override
    public Sport findByName(Sport sport) {
        String name = sport.getName();
        if (name == null) {
            throw new DaoException("Cannot find sport by name if name is null");
        }

        String sql = "SELECT id, uuid, deleted, name " +
                "FROM Sport WHERE name = " + "'" + name + "'";

        try (Statement st = getConnection().createStatement()) {
            try (ResultSet rs = st.executeQuery(sql)) {
                if (rs.next()) {
                    sport = getSportFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Could not fetch Sport by name " + name, e);
        }
        return sport;
    }

    private Sport getSportFromResultSet(ResultSet resultSet) throws SQLException {
        Sport sport = new Sport();
        sport.setId(resultSet.getInt("id"));
        sport.setUuid((UUID) resultSet.getObject("uuid"));
        sport.setDeleted(resultSet.getBoolean("deleted"));
        sport.setName(resultSet.getString("name"));
        return sport;
    }
}

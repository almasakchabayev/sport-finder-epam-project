package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.FloorCoverageDao;
import com.epam.aa.sportfinder.dao.SportDao;
import com.epam.aa.sportfinder.model.FloorCoverage;
import com.epam.aa.sportfinder.model.Sport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class JdbcSportDao extends JdbcBaseDao<Sport> implements SportDao {
    private static final Logger logger = LoggerFactory.getLogger(JdbcSportDao.class);

    private static final String SQL_FIND_BY_ID =
            "SELECT id, uuid, deleted, name " +
                    "FROM Sport WHERE id = ?";

    public JdbcSportDao(Connection connection) {
        super(connection);
    }

    @Override
    public Sport findById(Integer id) {
        Sport sport;
        if (id == null) throw new DaoException(new NullPointerException("Could not find element by id, id is null"));
        if (id < 1) throw new DaoException(new IllegalArgumentException("Could not find elemen tby id, id cannot be less than 1"));
        try (PreparedStatement pst = getConnection().prepareStatement(SQL_FIND_BY_ID)){
            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) sport = map(rs);
                else throw new DaoException("Could not find element by id " + id);
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find element by id " + id, e);
        }
        return sport;
    }

    private static Sport map(ResultSet resultSet) throws SQLException {
        Sport sport = new Sport();
        sport.setId(resultSet.getInt("id"));
        sport.setUuid((UUID) resultSet.getObject("uuid"));
        sport.setDeleted(resultSet.getBoolean("deleted"));
        sport.setName(resultSet.getString("name"));
        return sport;
    }
}

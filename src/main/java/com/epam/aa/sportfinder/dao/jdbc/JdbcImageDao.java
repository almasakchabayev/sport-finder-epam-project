package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.ImageDao;
import com.epam.aa.sportfinder.model.Image;
import com.epam.aa.sportfinder.model.SportPlace;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcImageDao extends JdbcBaseDao<Image> implements ImageDao {
    public JdbcImageDao(Connection connection) {
        super(connection);
    }

    @Override
    public Long getModifiedAt(Integer id) {
        if (id == null)
            throw new DaoException("cannot getModifiedAt for null id");

        String sql = "SELECT modifiedAt FROM Image WHERE id = " + id;

        try (Statement st = getConnection().createStatement()){
            try (ResultSet rs = st.executeQuery(sql)) {
                if (rs.next()) {
                    return rs.getTimestamp("modifiedAt").getTime();
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Insertion failed", e);
        }
        return null;
    }
}

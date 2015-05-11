package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.ImageDao;
import com.epam.aa.sportfinder.model.Image;

import java.sql.Connection;

public class JdbcImageDao extends JdbcBaseDao<Image> implements ImageDao {
    public JdbcImageDao(Connection connection) {
        super(connection);
    }
}

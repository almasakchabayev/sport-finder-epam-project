package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.ImageDao;
import com.epam.aa.sportfinder.model.Image;
import com.epam.aa.sportfinder.model.SportPlace;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcImageDao extends JdbcBaseDao<Image> implements ImageDao {
    public JdbcImageDao(Connection connection) {
        super(connection);
    }
}

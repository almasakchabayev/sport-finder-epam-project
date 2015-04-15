package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.config.AppProperties;
import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.DaoFactory;
import com.epam.aa.sportfinder.dao.DaoManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.sql.SQLException;

public class JdbcDaoFactory extends DaoFactory {
    private static final DataSource dataSource = initJdbc();

    private static DataSource initJdbc() {
        HikariConfig config = new HikariConfig(AppProperties.HIKARI_PROPERTIES_PATH);
        DataSource ds = new HikariDataSource(config);

        Flyway flyway = new Flyway();
        flyway.setDataSource(ds);
        flyway.migrate();

        return ds;
    }

    @Override
    public DaoManager createDaoManager() {
        try {
            return new JdbcDaoManager(dataSource.getConnection());
        } catch (SQLException e) {
            throw new DaoException("Could not get connection from dataSource", e);
        }
    }
}

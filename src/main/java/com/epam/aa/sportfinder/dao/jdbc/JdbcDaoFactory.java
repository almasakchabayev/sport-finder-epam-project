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
    private static JdbcDaoFactory instance = new JdbcDaoFactory();
    private final DataSource dataSource = initDataSource();

    private JdbcDaoFactory() {
    }

    public static JdbcDaoFactory getInstance() {
        return instance;
    }

    private DataSource initDataSource() {
        HikariConfig config = new HikariConfig(AppProperties.HIKARI_PROPERTIES_PATH);
        DataSource ds = new HikariDataSource(config);

        applyMigrations(ds);

        return ds;
    }

    private void applyMigrations(DataSource ds) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(ds);
        if (AppProperties.getFlywayProperty("clean").equals("true"))
            flyway.clean();
            flyway.clean();
        flyway.migrate();
    }

    @Override
    public DaoManager createDaoManager() {
        try {
            return new JdbcDaoManager(dataSource.getConnection());
        } catch (SQLException e) {
            throw new DaoException("Could not get connection from dataSource", e);
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}

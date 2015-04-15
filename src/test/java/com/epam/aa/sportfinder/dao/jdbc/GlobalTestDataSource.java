package com.epam.aa.sportfinder.dao.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

public abstract class GlobalTestDataSource {
    private static final DataSource dataSource = initDataSource();

    private static DataSource initDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setMaximumPoolSize(100);
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setJdbcUrl("jdbc:postgresql://localhost:5432/sportfindertestdb"); ;
        ds.setUsername("sportfindertestdb");
        ds.setPassword("sportfindertestdb");

        Flyway flyway = new Flyway();
        flyway.setLocations("db/migration", "data");
        flyway.setDataSource(ds);
        flyway.clean();
        flyway.migrate();

        return ds;
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}

package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoFactory;
import com.epam.aa.sportfinder.dao.DaoManager;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

public abstract class GlobalTestDataSource {
    public static final DaoFactory DAO_FACTORY = DaoFactory.getInstance(DaoFactory.Type.JDBC);
//    private static final DataSource dataSource = initDataSource();

//    private static DataSource initDataSource() {
//        DaoFactory.setDefaultType("jdbc");
//
//        HikariDataSource ds = new HikariDataSource();
//        ds.setMaximumPoolSize(100);
//        ds.setDriverClassName("org.postgresql.Driver");
//        ds.setJdbcUrl("jdbc:postgresql://localhost:5432/sportfindertestdb"); ;
//        ds.setUsername("sportfindertestdb");
//        ds.setPassword("sportfindertestdb");
//
//        Flyway flyway = new Flyway();
//        flyway.setLocations("db/migration", "db");
//        flyway.setDataSource(ds);
//        flyway.clean();
//        flyway.migrate();
//
//        return ds;
//    }

    public static DataSource getDataSource() {
        JdbcDaoFactory daoFactory = (JdbcDaoFactory) DAO_FACTORY;
        return daoFactory.getDataSource();
    }

    public static DaoManager getDaoManager() {
        return DAO_FACTORY.createDaoManager();
    }
}

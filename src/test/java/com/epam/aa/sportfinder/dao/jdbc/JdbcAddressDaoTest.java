package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.model.Address;
import org.flywaydb.core.Flyway;
import org.h2.Driver;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JdbcAddressDaoTest {
    private static JdbcDataSource dataSource;

    @BeforeClass
    public static void setUp() throws Exception {
        Driver driver = new org.h2.Driver();
        String dbUrl = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=1;MODE=PostgreSQL;";
        dataSource = new JdbcDataSource();
        dataSource.setURL(dbUrl);

        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setLocations("db/migration", "data");
        flyway.clean();
        flyway.migrate();
    }

    @Test
    public void testCreate() throws Exception {
        Address address = new Address();
        address.setCountry("Kazakhstan");
        address.setCity("Almaty");
        address.setAddressLine1("Amangeldy street, 72");
        address.setAddressLine2("1");
        address.setZipcode("050012");

        JdbcAddressDao dao = new JdbcAddressDao(dataSource.getConnection());

        dao.create(address);
    }

    @Test
    public void testFind() throws Exception {
        JdbcAddressDao dao = new JdbcAddressDao(dataSource.getConnection());
        Address address = dao.find(1);
        assertEquals(1, address.getId().intValue());
        assertEquals("USA", address.getCountry());
        assertEquals("New York", address.getCity());
        assertEquals("350 Fifth Avenue", address.getAddressLine1());
        assertEquals("34th floor", address.getAddressLine2());
        assertEquals("NY 10118", address.getZipcode());
    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }
}
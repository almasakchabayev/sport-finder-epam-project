package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.model.Address;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JdbcAddressDaoTest extends GlobalTestDataSource {
    @Test
    public void testCreate() throws Exception {
        Address address = new Address();
        address.setCountry("Kazakhstan");
        address.setCity("Almaty");
        address.setAddressLine1("Amangeldy street, 72");
        address.setAddressLine2("1");
        address.setZipcode("050012");

        JdbcAddressDao dao = new JdbcAddressDao(getDataSource().getConnection());

        dao.create(address);
    }

    @Test
    public void testFind() throws Exception {
        JdbcAddressDao dao = new JdbcAddressDao(getDataSource().getConnection());
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
        JdbcAddressDao dao = new JdbcAddressDao(getDataSource().getConnection());
        Address address = new Address();
        address.setId(1);
        address.setCountry("UK");
        address.setCity("London");
        address.setAddressLine1("Canary Wharf, 72");
        address.setAddressLine2("5th floor");
        address.setZipcode("CV47AL");

        dao.update(address);

        Address result = dao.find(1);
        assertEquals(1, result.getId().intValue());
        assertEquals("UK", result.getCountry());
        assertEquals("London", result.getCity());
        assertEquals("Canary Wharf, 72", result.getAddressLine1());
        assertEquals("5th floor", result.getAddressLine2());
        assertEquals("CV47AL", result.getZipcode());
    }

    @Test
    public void testDelete() throws Exception {

    }
}
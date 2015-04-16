package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.model.Address;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class JdbcAddressDaoTest extends GlobalTestDataSource {


    @Test(expected = DaoException.class)
    public void testInsertNewAddressFailsIfIdNotNull() throws Exception {
        Address address = new Address();
        address.setId(1);

        Connection connection = getDataSource().getConnection();
        JdbcAddressDao dao = new JdbcAddressDao(connection);
        dao.insert(address);
        connection.close();
    }

    @Test(expected = DaoException.class)
    public void testInsertNewAddressFailsIfUuidAlreadyExists() throws Exception {
        Address address = new Address();
        Connection connection = getDataSource().getConnection();
        JdbcAddressDao dao = new JdbcAddressDao(connection);
        dao.insert(address);

        Address addressWithSameUuid = new Address();
        addressWithSameUuid.setUuid(address.getUuid());
        JdbcAddressDao dao2 = new JdbcAddressDao(connection);
        dao2.insert(addressWithSameUuid);

        connection.close();
    }

    @Test
    public void testInsertNewAddressWithoutIdAndUuid() throws Exception {
        Address address = new Address();
        address.setCountry("Kazakhstan");
        address.setCity("Almaty");
        address.setAddressLine1("Amangeldy street, 72");
        address.setAddressLine2("1");
        address.setZipcode("050012");

        JdbcAddressDao dao = new JdbcAddressDao(getDataSource().getConnection());
        dao.insert(address);

        Statement st = getDataSource().getConnection().createStatement();
        ResultSet resultSet = st.executeQuery("SELECT id, uuid, deleted, country, city, addressLine1, addressLine2, zipcode " +
                "FROM Address ORDER BY id DESC LIMIT 1");
        Address addressFromDatabase = new Address();

        if (resultSet.next()) {
            addressFromDatabase.setId(resultSet.getInt("id"));
            addressFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
            addressFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
            addressFromDatabase.setCountry(resultSet.getString("country"));
            addressFromDatabase.setCity(resultSet.getString("city"));
            addressFromDatabase.setAddressLine1(resultSet.getString("addressLine1"));
            addressFromDatabase.setAddressLine2(resultSet.getString("addressLine2"));
            addressFromDatabase.setZipcode(resultSet.getString("zipcode"));
        }
        resultSet.close();
        st.close();


        assertEquals(address.getId(), addressFromDatabase.getId());
        assertEquals(address.getUuid(), addressFromDatabase.getUuid());
        assertEquals(address.isDeleted(), address.isDeleted());
        assertEquals(address.getCountry(), addressFromDatabase.getCountry());
        assertEquals(address.getCity(), addressFromDatabase.getCity());
        assertEquals(address.getAddressLine1(), addressFromDatabase.getAddressLine1());
        assertEquals(address.getAddressLine2(), addressFromDatabase.getAddressLine2());
        assertEquals(address.getZipcode(), addressFromDatabase.getZipcode());
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
        address.setId(2);
        address.setCountry("UK");
        address.setCity("London");
        address.setAddressLine1("Canary Wharf, 72");
        address.setAddressLine2("5th floor");
        address.setZipcode("CV47AL");

        dao.update(address);

        Address result = dao.find(2);
        assertEquals(2, result.getId().intValue());
        assertEquals("UK", result.getCountry());
        assertEquals("London", result.getCity());
        assertEquals("Canary Wharf, 72", result.getAddressLine1());
        assertEquals("5th floor", result.getAddressLine2());
        assertEquals("CV47AL", result.getZipcode());
    }

    @Test
    public void testDelete() throws Exception {
        JdbcAddressDao dao = new JdbcAddressDao(getDataSource().getConnection());
        Address address = new Address();
        address.setId(1);
        dao.delete(address);
    }
}
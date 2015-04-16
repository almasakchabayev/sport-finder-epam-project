package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.model.Address;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class JdbcAddressDaoTest extends GlobalTestDataSource {


    @Test(expected = DaoException.class)
    public void testInsertFailsIfIdNotNull() throws Exception {
        Connection connection = getDataSource().getConnection();
        JdbcAddressDao dao = new JdbcAddressDao(connection);

        Address address = new Address();
        address.setId(1);

        dao.insert(address);
        connection.close();
    }

    @Test(expected = DaoException.class)
    public void testInsertFailsIfUuidAlreadyExists() throws Exception {
        Connection connection = getDataSource().getConnection();
        JdbcAddressDao dao = new JdbcAddressDao(connection);

        Address address = new Address();
        dao.insert(address);

        Address addressWithSameUuid = new Address();
        addressWithSameUuid.setUuid(address.getUuid());

        dao.insert(addressWithSameUuid);
        connection.close();
    }

    @Test
    public void testInsertSuccessWithoutIdAndUuid() throws Exception {
        Connection connection = getDataSource().getConnection();
        JdbcAddressDao dao = new JdbcAddressDao(getDataSource().getConnection());

        Address address = new Address();
        address.setCountry("Kazakhstan");
        address.setCity("Almaty");
        address.setAddressLine1("Amangeldy street, 72");
        address.setAddressLine2("1");
        address.setZipcode("050012");

        dao.insert(address);

        Statement st = connection.createStatement();
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
        connection.close();

        assertAddressesEqual(address, addressFromDatabase);
    }

    @Test(expected = DaoException.class)
    public void testUpdateFailsIfIdIsNull() throws Exception {
        Connection connection = getDataSource().getConnection();
        JdbcAddressDao dao = new JdbcAddressDao(getDataSource().getConnection());

        Address address = new Address();
        address.setCountry("UK");
        address.setCity("London");
        address.setAddressLine1("Canary Wharf, 72");
        address.setAddressLine2("5th floor");
        address.setZipcode("CV47AL");

        dao.update(address);
        connection.close();
    }

    @Test
    public void testUpdateSuccessIfIdNotNull() throws Exception {
        Connection connection = getDataSource().getConnection();
        JdbcAddressDao dao = new JdbcAddressDao(connection);
        Address address = new Address();
        address.setId(2);
        address.setCountry("UK");
        address.setCity("London");
        address.setAddressLine1("Canary Wharf, 72");
        address.setAddressLine2("5th floor");
        address.setZipcode("CV47AL");

        dao.update(address);

        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, country, city, addressLine1, addressLine2, zipcode " +
                "FROM Address WHERE id = ?");
        pst.setInt(1, address.getId());
        ResultSet resultSet = pst.executeQuery();
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
        pst.close();
        connection.close();

        assertAddressesEqual(address, addressFromDatabase);
    }

    @Test
    public void testDeleteInDbAndAssignTrueToObject() throws Exception {
        Connection connection = getDataSource().getConnection();
        JdbcAddressDao dao = new JdbcAddressDao(connection);
        Address address = new Address();
        address.setId(1);
        dao.delete(address);

        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, country, city, addressLine1, addressLine2, zipcode " +
                "FROM Address WHERE id = ?");
        pst.setInt(1, address.getId());
        ResultSet resultSet = pst.executeQuery();
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
        pst.close();
        connection.close();

        assertEquals(addressFromDatabase.isDeleted(), true);
        assertEquals(address.isDeleted(), addressFromDatabase.isDeleted());
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfIdIsNull() throws Exception {
        Connection connection = getDataSource().getConnection();
        JdbcAddressDao dao = new JdbcAddressDao(connection);
        Address dummyAddress = new Address();
        Address address = dao.findById(dummyAddress.getId());
        connection.close();
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfIdIsNegative() throws Exception {
        Connection connection = getDataSource().getConnection();
        JdbcAddressDao dao = new JdbcAddressDao(connection);
        Address address = dao.findById(-1);
        connection.close();
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfElementCouldNotBeFounded() throws Exception {
        Connection connection = getDataSource().getConnection();
        JdbcAddressDao dao = new JdbcAddressDao(connection);
        Address address = dao.findById(100000000);
        connection.close();
    }

    @Test
    public void testFindByIdSuccessIfValidId() throws Exception {
        Connection connection = getDataSource().getConnection();
        JdbcAddressDao dao = new JdbcAddressDao(connection);
        Address address = dao.findById(1);

        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, country, city, addressLine1, addressLine2, zipcode " +
                "FROM Address WHERE id = ?");
        pst.setInt(1, address.getId());
        ResultSet resultSet = pst.executeQuery();
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

        connection.close();

        assertAddressesEqual(address, addressFromDatabase);
    }

    public void assertAddressesEqual(Address address, Address addressFromDatabase) {
        assertEquals(address.getId(), addressFromDatabase.getId());
        assertEquals(address.getUuid(), addressFromDatabase.getUuid());
        assertEquals(address.isDeleted(), address.isDeleted());
        assertEquals(address.getCountry(), addressFromDatabase.getCountry());
        assertEquals(address.getCity(), addressFromDatabase.getCity());
        assertEquals(address.getAddressLine1(), addressFromDatabase.getAddressLine1());
        assertEquals(address.getAddressLine2(), addressFromDatabase.getAddressLine2());
        assertEquals(address.getZipcode(), addressFromDatabase.getZipcode());
    }
}
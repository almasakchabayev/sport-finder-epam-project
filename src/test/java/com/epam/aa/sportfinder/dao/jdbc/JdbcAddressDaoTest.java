package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.AddressDao;
import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.DaoManager;
import com.epam.aa.sportfinder.model.Address;
import org.junit.After;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class JdbcAddressDaoTest extends TestConfig {
    @Test(expected = DaoException.class)
    public void testInsertFailsIfIdNotNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            AddressDao dao = daoManager.getDao(Address.class);

            Address address = new Address();
            address.setId(1);

            return dao.insert(address);
        });
    }

    @Test(expected = DaoException.class)
    public void testInsertFailsIfUuidAlreadyExists() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            AddressDao dao = daoManager.getDao(Address.class);

            Address address = new Address();
            dao.insert(address);

            Address addressWithSameUuid = new Address();
            addressWithSameUuid.setUuid(address.getUuid());

            return dao.insert(addressWithSameUuid);
        });
    }

    @Test
    public void testInsertSuccessWithoutIdAndUuid() throws Exception {
        DaoManager daoManager = getDaoManager();
        Address address = new Address();
        daoManager.executeTx(daoManager1 -> {
            AddressDao dao = daoManager.getDao(Address.class);

            address.setCountry("Kazakhstan");
            address.setCity("Almaty");
            address.setAddressLine1("Amangeldy street, 72");
            address.setAddressLine2("1");
            address.setZipcode("050012");

            return dao.insert(address);
        });

        Connection connection = getDataSource().getConnection();
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
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            AddressDao dao = daoManager.getDao(Address.class);

            Address address = new Address();
            address.setCountry("UK");
            address.setCity("London");
            address.setAddressLine1("Canary Wharf, 72");
            address.setAddressLine2("5th floor");
            address.setZipcode("CV47AL");

            dao.update(address);
            return null;
        });
    }

    @Test
    public void testUpdateSuccessIfIdNotNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        Address address = new Address();
        daoManager.executeTx(daoManager1 -> {
            AddressDao dao = daoManager.getDao(Address.class);
            address.setId(2);
            address.setCountry("UK");
            address.setCity("London");
            address.setAddressLine1("Canary Wharf, 72");
            address.setAddressLine2("5th floor");
            address.setZipcode("CV47AL");

            dao.update(address);
            return null;
        });

        Connection connection = getDataSource().getConnection();
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
        DaoManager daoManager = getDaoManager();
        Address address = new Address();
        daoManager.executeTx(daoManager1 -> {
            AddressDao dao = daoManager.getDao(Address.class);
            address.setId(1);
            return dao.delete(address);
        });

        Connection connection = getDataSource().getConnection();
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
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            AddressDao dao = daoManager.getDao(Address.class);
            Address dummyAddress = new Address();
            return dao.findById(dummyAddress.getId());
        });
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfIdIsNegative() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            AddressDao dao = daoManager.getDao(Address.class);
            return dao.findById(-1);
        });
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfElementCouldNotBeFounded() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            AddressDao dao = daoManager.getDao(Address.class);
            return dao.findById(100000000);
        });
    }

    @Test
    public void testFindByIdSuccessIfValidId() throws Exception {
        DaoManager daoManager = getDaoManager();
        Address address = daoManager.executeTx(daoManager1 -> {
            AddressDao dao = daoManager.getDao(Address.class);
            return dao.findById(1);
        });

        Connection connection = getDataSource().getConnection();
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
        assertEquals(address.isDeleted(), addressFromDatabase.isDeleted());
        assertEquals(address.getCountry(), addressFromDatabase.getCountry());
        assertEquals(address.getCity(), addressFromDatabase.getCity());
        assertEquals(address.getAddressLine1(), addressFromDatabase.getAddressLine1());
        assertEquals(address.getAddressLine2(), addressFromDatabase.getAddressLine2());
        assertEquals(address.getZipcode(), addressFromDatabase.getZipcode());
    }
}
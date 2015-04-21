package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.DaoManager;
import com.epam.aa.sportfinder.dao.PhoneNumberDao;
import com.epam.aa.sportfinder.model.PhoneNumber;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class JdbcPhoneNumberDaoTest extends TestConfig {


    @Test(expected = DaoException.class)
    public void testInsertFailsIfIdNotNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        PhoneNumberDao dao = daoManager.getDao(PhoneNumber.class);

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setId(1);

        dao.insert(phoneNumber);
    }

    @Test(expected = DaoException.class)
    public void testInsertFailsIfUuidAlreadyExists() throws Exception {
        DaoManager daoManager = getDaoManager();
        PhoneNumberDao dao = daoManager.getDao(PhoneNumber.class);

        PhoneNumber phoneNumber = new PhoneNumber();
        dao.insert(phoneNumber);

        PhoneNumber phoneNumberWithSameUuid = new PhoneNumber();
        phoneNumberWithSameUuid.setUuid(phoneNumber.getUuid());

        dao.insert(phoneNumberWithSameUuid);
    }

    @Test
    public void testInsertSuccessWithoutIdAndUuid() throws Exception {
        DaoManager daoManager = getDaoManager();
        PhoneNumberDao dao = daoManager.getDao(PhoneNumber.class);

        Connection connection = getDataSource().getConnection();

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setNumber("87017654321");

        dao.insert(phoneNumber);

        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT id, uuid, deleted, number " +
                "FROM PhoneNumber ORDER BY id DESC LIMIT 1");
        PhoneNumber phoneNumberFromDatabase = new PhoneNumber();

        if (resultSet.next()) {
            phoneNumberFromDatabase.setId(resultSet.getInt("id"));
            phoneNumberFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
            phoneNumberFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
            phoneNumberFromDatabase.setNumber(resultSet.getString("number"));
        }
        resultSet.close();
        st.close();
        connection.close();

        assertPhoneNumbersEqual(phoneNumber, phoneNumberFromDatabase);
    }

    @Test(expected = DaoException.class)
    public void testUpdateFailsIfIdIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        PhoneNumberDao dao = daoManager.getDao(PhoneNumber.class);

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setNumber("1234567890");

        dao.update(phoneNumber);
    }

    @Test
    public void testUpdateSuccessIfIdNotNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        PhoneNumberDao dao = daoManager.getDao(PhoneNumber.class);

        Connection connection = getDataSource().getConnection();

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setId(2);
        phoneNumber.setNumber("lenoleum");

        dao.update(phoneNumber);

        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, number " +
                "FROM PhoneNumber WHERE id = ?");
        pst.setInt(1, phoneNumber.getId());
        ResultSet resultSet = pst.executeQuery();
        PhoneNumber phoneNumberFromDatabase = new PhoneNumber();

        if (resultSet.next()) {
            phoneNumberFromDatabase.setId(resultSet.getInt("id"));
            phoneNumberFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
            phoneNumberFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
            phoneNumberFromDatabase.setNumber(resultSet.getString("number"));
        }
        resultSet.close();
        pst.close();
        connection.close();

        assertPhoneNumbersEqual(phoneNumber, phoneNumberFromDatabase);
    }

    @Test
    public void testDeleteInDbAndAssignTrueToObject() throws Exception {
        DaoManager daoManager = getDaoManager();
        PhoneNumberDao dao = daoManager.getDao(PhoneNumber.class);

        Connection connection = getDataSource().getConnection();

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setId(1);
        dao.delete(phoneNumber);

        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, number " +
                "FROM PhoneNumber WHERE id = ?");
        pst.setInt(1, phoneNumber.getId());
        ResultSet resultSet = pst.executeQuery();
        PhoneNumber phoneNumberFromDatabase = new PhoneNumber();

        if (resultSet.next()) {
            phoneNumberFromDatabase.setId(resultSet.getInt("id"));
            phoneNumberFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
            phoneNumberFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
            phoneNumberFromDatabase.setNumber(resultSet.getString("number"));
        }
        resultSet.close();
        pst.close();
        connection.close();

        assertEquals(phoneNumberFromDatabase.isDeleted(), true);
        assertEquals(phoneNumber.isDeleted(), phoneNumberFromDatabase.isDeleted());
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfIdIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        PhoneNumberDao dao = daoManager.getDao(PhoneNumber.class);

        PhoneNumber dummyphoneNumber = new PhoneNumber();
        PhoneNumber phoneNumber = dao.findById(dummyphoneNumber.getId());
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfIdIsNegative() throws Exception {
        DaoManager daoManager = getDaoManager();
        PhoneNumberDao dao = daoManager.getDao(PhoneNumber.class);

        PhoneNumber phoneNumber = dao.findById(-1);
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfElementCouldNotBeFounded() throws Exception {
        DaoManager daoManager = getDaoManager();
        PhoneNumberDao dao = daoManager.getDao(PhoneNumber.class);
        PhoneNumber phoneNumber = dao.findById(100000000);
    }

    @Test
    public void testFindByIdSuccessIfValidId() throws Exception {
        DaoManager daoManager = getDaoManager();
        PhoneNumberDao dao = daoManager.getDao(PhoneNumber.class);

        Connection connection = getDataSource().getConnection();
        PhoneNumber phoneNumber = dao.findById(1);

        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, number " +
                "FROM PhoneNumber WHERE id = ?");
        pst.setInt(1, phoneNumber.getId());
        ResultSet resultSet = pst.executeQuery();
        PhoneNumber phoneNumberFromDatabase = new PhoneNumber();

        if (resultSet.next()) {
            phoneNumberFromDatabase.setId(resultSet.getInt("id"));
            phoneNumberFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
            phoneNumberFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
            phoneNumberFromDatabase.setNumber(resultSet.getString("number"));}

        connection.close();

        assertPhoneNumbersEqual(phoneNumber, phoneNumberFromDatabase);
    }

    public void assertPhoneNumbersEqual(PhoneNumber phoneNumber, PhoneNumber phoneNumberFromDatabase) {
        assertEquals(phoneNumber.getId(), phoneNumberFromDatabase.getId());
        assertEquals(phoneNumber.getUuid(), phoneNumberFromDatabase.getUuid());
        assertEquals(phoneNumber.isDeleted(), phoneNumberFromDatabase.isDeleted());
        assertEquals(phoneNumber.getNumber(), phoneNumberFromDatabase.getNumber());

    }
}
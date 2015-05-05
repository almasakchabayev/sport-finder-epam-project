package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoCommand;
import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.DaoManager;
import com.epam.aa.sportfinder.dao.PhoneNumberDao;
import com.epam.aa.sportfinder.model.PhoneNumber;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class JdbcPhoneNumberDaoTest extends TestConfig {


    @Test(expected = DaoException.class)
    public void testInsertFailsIfIdNotNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            PhoneNumberDao dao = daoManager.getDao(PhoneNumber.class);

            PhoneNumber phoneNumber = new PhoneNumber();
            phoneNumber.setId(1);

            return dao.insert(phoneNumber);
        });
    }

    @Test(expected = DaoException.class)
    public void testInsertFailsIfUuidAlreadyExists() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            PhoneNumberDao dao = daoManager.getDao(PhoneNumber.class);

            PhoneNumber phoneNumber = new PhoneNumber();
            dao.insert(phoneNumber);

            PhoneNumber phoneNumberWithSameUuid = new PhoneNumber();
            phoneNumberWithSameUuid.setUuid(phoneNumber.getUuid());

            return dao.insert(phoneNumberWithSameUuid);
        });
    }

    @Test
    public void testInsertSuccessWithoutIdAndUuid() throws Exception {
        DaoManager daoManager = getDaoManager();
        PhoneNumber phoneNumber = new PhoneNumber();
        daoManager.executeTx(daoManager1 -> {
            PhoneNumberDao dao = daoManager.getDao(PhoneNumber.class);
            phoneNumber.setNumber("87017654321");
            return dao.insert(phoneNumber);
        });

        Connection connection = getDataSource().getConnection();
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
        daoManager.executeTx(daoManager1 -> {
            PhoneNumberDao dao = daoManager.getDao(PhoneNumber.class);

            PhoneNumber phoneNumber = new PhoneNumber();
            phoneNumber.setNumber("1234567890");

            dao.update(phoneNumber);
            return null;
        });
    }

    @Test
    public void testUpdateSuccessIfIdNotNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        PhoneNumber phoneNumber = new PhoneNumber();
        daoManager.executeTx(daoManager1 -> {
            PhoneNumberDao dao = daoManager.getDao(PhoneNumber.class);
            phoneNumber.setId(2);
            phoneNumber.setNumber("00000000000");
            dao.update(phoneNumber);
            return null;
        });

        Connection connection = getDataSource().getConnection();
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
        PhoneNumber phoneNumber = new PhoneNumber();
        daoManager.executeTx(daoManager1 -> {
            PhoneNumberDao dao = daoManager.getDao(PhoneNumber.class);
            phoneNumber.setId(1);
            return dao.delete(phoneNumber);
        });

        Connection connection = getDataSource().getConnection();
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
        PhoneNumber phoneNumber = daoManager.executeTx(daoManager1 -> {
            PhoneNumberDao dao = daoManager.getDao(PhoneNumber.class);
            PhoneNumber dummyphoneNumber = new PhoneNumber();
            return dao.findById(dummyphoneNumber.getId());
        });
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfIdIsNegative() throws Exception {
        DaoManager daoManager = getDaoManager();
        PhoneNumber phoneNumber = daoManager.executeTx(daoManager1 -> {
            PhoneNumberDao dao = daoManager.getDao(PhoneNumber.class);
            return dao.findById(-1);
        });
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfElementCouldNotBeFounded() throws Exception {
        DaoManager daoManager = getDaoManager();
        PhoneNumber phoneNumber = daoManager.executeTx(daoManager1 -> {
            PhoneNumberDao dao = daoManager.getDao(PhoneNumber.class);
            return dao.findById(100000000);
        });
    }

    @Test
    public void testFindByIdSuccessIfValidId() throws Exception {
        DaoManager daoManager = getDaoManager();
        PhoneNumber phoneNumber = daoManager.executeTx(daoManager1 -> {
            PhoneNumberDao dao = daoManager.getDao(PhoneNumber.class);
            return dao.findById(1);
        });

        Connection connection = getDataSource().getConnection();
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
    public void testFindByIdsSuccessIfValidId() throws Exception {
        DaoManager daoManager = getDaoManager();

        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(3);

        List<PhoneNumber> phoneNumbers = daoManager.executeTx(daoManager1 -> {
            PhoneNumberDao dao = daoManager1.getDao(PhoneNumber.class);
            return dao.findByIds(ids);
        });

        Connection connection = getDataSource().getConnection();
        String sql = "SELECT id, uuid, deleted, number " +
                "FROM PhoneNumber WHERE ";

        String prefix = "";
        for (Integer id : ids) {
            sql += prefix;
            prefix = "AND ";
            sql += "id = " + id +" ";
        }
        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet resultSet = pst.executeQuery();

        List<PhoneNumber> phoneNumbersFromDatabase = new ArrayList<>();
        while (resultSet.next()) {
            PhoneNumber phoneNumberFromDatabase = new PhoneNumber();
            phoneNumberFromDatabase.setId(resultSet.getInt("id"));
            phoneNumberFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
            phoneNumberFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
            phoneNumberFromDatabase.setNumber(resultSet.getString("number"));
            phoneNumbersFromDatabase.add(phoneNumberFromDatabase);
        }
        resultSet.close();
        pst.close();
        connection.close();

        assertEquals(phoneNumbers.size(), phoneNumbersFromDatabase.size());
        for (int i = 0; i < phoneNumbers.size(); i++) {
            assertPhoneNumbersEqual(phoneNumbers.get(i), phoneNumbersFromDatabase.get(i));
        }
    }

    public void assertPhoneNumbersEqual(PhoneNumber phoneNumber, PhoneNumber phoneNumberFromDatabase) {
        assertEquals(phoneNumber.getId(), phoneNumberFromDatabase.getId());
        assertEquals(phoneNumber.getUuid(), phoneNumberFromDatabase.getUuid());
        assertEquals(phoneNumber.isDeleted(), phoneNumberFromDatabase.isDeleted());
        assertEquals(phoneNumber.getNumber(), phoneNumberFromDatabase.getNumber());

    }
}
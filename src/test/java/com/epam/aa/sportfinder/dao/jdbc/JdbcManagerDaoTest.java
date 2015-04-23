package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoCommand;
import com.epam.aa.sportfinder.dao.ManagerDao;
import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.DaoManager;
import com.epam.aa.sportfinder.model.Address;
import com.epam.aa.sportfinder.model.Company;
import com.epam.aa.sportfinder.model.Manager;
import com.epam.aa.sportfinder.model.PhoneNumber;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class JdbcManagerDaoTest extends TestConfig {


    @Test(expected = DaoException.class)
    public void testInsertFailsIfIdNotNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        ManagerDao dao = daoManager.getDao(Manager.class);

        Manager manager = new Manager();
        manager.setId(1);

        dao.insert(manager);
    }

    @Test(expected = DaoException.class)
    public void testInsertFailsIfUuidAlreadyExists() throws Exception {
        DaoManager daoManager = getDaoManager();
        ManagerDao dao = daoManager.getDao(Manager.class);

        Manager manager = new Manager();
        dao.insert(manager);

        Manager managerWithSameUuid = new Manager();
        managerWithSameUuid.setUuid(manager.getUuid());

        dao.insert(managerWithSameUuid);
    }

    @Test(expected = DaoException.class)
    public void testInsertFailsIfEmailAlreadyExists() throws Exception {
        DaoManager daoManager = getDaoManager();
        Manager manager = daoManager.executeTx(daoManager1 -> {
            ManagerDao dao = daoManager1.getDao(Manager.class);
            Manager insert = new Manager();
            insert.setEmail("almas@gmail.com");
            return dao.insert(insert);
        });



        DaoManager daoManager1 = getDaoManager();
        Manager manager1 = daoManager1.executeTx(daoManager2 -> {
            ManagerDao dao = daoManager1.getDao(Manager.class);
            Manager managerWithSameName = new Manager();
            managerWithSameName.setEmail(manager.getEmail());
            return dao.insert(managerWithSameName);
        });
    }

    @Test
    public void testInsertSuccessWithoutIdAndUuid() throws Exception {
        DaoManager daoManager = getDaoManager();
        ManagerDao dao = daoManager.getDao(Manager.class);

        Connection connection = getDataSource().getConnection();

        Manager manager = new Manager();
        initManager(manager);

        dao.insert(manager);

        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT id, uuid, deleted, firstName, lastName, " +
                "email, password, company " +
                "FROM Manager ORDER BY id DESC LIMIT 1");

        Manager managerFromDatabase = new Manager();
        if (resultSet.next()) {
            mapResultSetToManager(resultSet, managerFromDatabase);
        }
        resultSet.close();
        st.close();
        connection.close();

        assertManagersEqual(manager, managerFromDatabase);
    }

    @Test
    public void testInsertPhoneNumbersSuccess() throws Exception {
        DaoManager daoManager = getDaoManager();
        ManagerDao dao = daoManager.getDao(Manager.class);

        Connection connection = getDataSource().getConnection();

        Manager manager = new Manager();
        initManager(manager);
        manager.setId(1);
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        PhoneNumber phoneNumber1 = new PhoneNumber();
        phoneNumber1.setId(1);
        phoneNumbers.add(phoneNumber1);
        PhoneNumber phoneNumber2 = new PhoneNumber();
        phoneNumber2.setId(2);
        phoneNumbers.add(phoneNumber2);
        manager.setPhoneNumbers(phoneNumbers);

        dao.insertPhoneNumbers(manager);

        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT manager_id, phoneNumber_id " +
                "FROM Manager_PhoneNumber WHERE manager_id = " + manager.getId());

        ArrayList<Integer> phoneNumberIdsFromDatabase = new ArrayList<>();
        while (resultSet.next()) {
            phoneNumberIdsFromDatabase.add(resultSet.getInt("phoneNumber_id"));
        }
        resultSet.close();
        st.close();
        connection.close();

        List<PhoneNumber> phoneNumberList = manager.getPhoneNumbers();
        assertEquals(phoneNumberList.size(), phoneNumberIdsFromDatabase.size());
        for (int i = 0; i < phoneNumberList.size(); i++) {
            assertEquals(phoneNumberList.get(i).getId(), phoneNumberIdsFromDatabase.get(i));
        }
    }


    @Test
    public void testAddPhoneNumberSuccess() throws Exception {
        DaoManager daoManager = getDaoManager();
        ManagerDao dao = daoManager.getDao(Manager.class);

        Connection connection = getDataSource().getConnection();

        Manager manager = new Manager();
        initManager(manager);
        manager.setId(2);
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setId(1);

        dao.addPhoneNumber(manager, phoneNumber);

        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT phoneNumber_id, manager_id " +
                "FROM Manager_PhoneNumber WHERE manager_id = " + manager.getId());


        ArrayList<Integer> phoneNumberIdsFromDatabase = new ArrayList<>();
        while (resultSet.next()) {
            phoneNumberIdsFromDatabase.add(resultSet.getInt("phoneNumber_id"));
        }

        resultSet.close();
        st.close();
        connection.close();

        assertEquals(true, phoneNumberIdsFromDatabase.contains(1));
    }

    @Test(expected = DaoException.class)
    public void testUpdateFailsIfIdIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        ManagerDao dao = daoManager.getDao(Manager.class);

        Manager manager = new Manager();
        initManager(manager);

        dao.update(manager);
    }

    @Test
    public void testUpdateSuccessIfIdNotNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        ManagerDao dao = daoManager.getDao(Manager.class);

        Connection connection = getDataSource().getConnection();

        Manager manager = new Manager();
        initManager(manager);
        manager.setEmail("Other email");
        manager.setId(2);

        dao.update(manager);

        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, firstName, lastName, " +
                "email, password, company " +
                "FROM Manager WHERE id = ?");
        pst.setInt(1, manager.getId());
        ResultSet resultSet = pst.executeQuery();

        Manager managerFromDatabase = new Manager();
        if (resultSet.next()) {
            mapResultSetToManager(resultSet, managerFromDatabase);
        }

        resultSet.close();
        pst.close();
        connection.close();

        assertManagersEqual(manager, managerFromDatabase);
    }

    @Test
    public void testDeleteInDbAndAssignTrueToObject() throws Exception {
        DaoManager daoManager = getDaoManager();
        ManagerDao dao = daoManager.getDao(Manager.class);

        Connection connection = getDataSource().getConnection();

        Manager manager = new Manager();
        manager.setId(1);
        dao.delete(manager);

        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, firstName, lastName, " +
                "email, password, company " +
                "FROM Manager WHERE id = ?");
        pst.setInt(1, manager.getId());
        ResultSet resultSet = pst.executeQuery();
        Manager managerFromDatabase = new Manager();

        if (resultSet.next()) {
            mapResultSetToManager(resultSet, managerFromDatabase);
        }
        resultSet.close();
        pst.close();
        connection.close();

        assertEquals(managerFromDatabase.isDeleted(), true);
        assertEquals(manager.isDeleted(), managerFromDatabase.isDeleted());
    }

    @Test
    public void testRemovePhoneNumberSuccess() throws Exception {
        DaoManager daoManager = getDaoManager();
        ManagerDao dao = daoManager.getDao(Manager.class);

        Connection connection = getDataSource().getConnection();

        Manager manager = new Manager();
        initManager(manager);
        manager.setId(2);
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setId(2);

        dao.removePhoneNumber(manager, phoneNumber);

        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT phoneNumber_id, manager_id " +
                "FROM Manager_PhoneNumber WHERE manager_id = " + manager.getId());


        ArrayList<Integer> phoneNumberIdsFromDatabase = new ArrayList<>();
        while (resultSet.next()) {
            phoneNumberIdsFromDatabase.add(resultSet.getInt("phoneNumber_id"));
        }

        resultSet.close();
        st.close();
        connection.close();

        assertEquals(false, phoneNumberIdsFromDatabase.contains(2));
        List<PhoneNumber> phoneNumberList = manager.getPhoneNumbers();
        assertEquals(phoneNumberList.size(), phoneNumberIdsFromDatabase.size());
        for (int i = 0; i < phoneNumberList.size(); i++) {
            assertEquals(phoneNumberList.get(i).getId(), phoneNumberIdsFromDatabase.get(i));
        }
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfIdIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        ManagerDao dao = daoManager.getDao(Manager.class);

        Manager dummymanager = new Manager();
        Manager manager = dao.findById(dummymanager.getId());
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfIdIsNegative() throws Exception {
        DaoManager daoManager = getDaoManager();
        ManagerDao dao = daoManager.getDao(Manager.class);

        Manager manager = dao.findById(-1);
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfElementCouldNotBeFounded() throws Exception {
        DaoManager daoManager = getDaoManager();
        ManagerDao dao = daoManager.getDao(Manager.class);
        Manager manager = dao.findById(100000000);
    }

    @Test
    public void testFindByIdSuccessIfValidId() throws Exception {
        DaoManager daoManager = getDaoManager();
        ManagerDao dao = daoManager.getDao(Manager.class);

        Connection connection = getDataSource().getConnection();
        Manager manager = dao.findById(1);

        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, firstName, lastName, " +
                "email, password, company " +
                "FROM Manager WHERE id = ?");
        pst.setInt(1, manager.getId());
        ResultSet resultSet = pst.executeQuery();

        Manager managerFromDatabase = new Manager();
        if (resultSet.next()) {
            mapResultSetToManager(resultSet, managerFromDatabase);
        }

        connection.close();

        assertManagersEqual(manager, managerFromDatabase);
    }

    @Test
    public void testFindByEmailSuccess() throws Exception {
        DaoManager daoManager = getDaoManager();

        Connection connection = getDataSource().getConnection();

        Manager manager = daoManager.executeTx(daoManager1 -> {
            ManagerDao dao = daoManager1.getDao(Manager.class);
            return dao.findByEmail("pg@gmail.com");
        });

        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, firstName, lastName, " +
                "email, password, company " +
                "FROM Manager WHERE email = ?");
        pst.setString(1, "pg@gmail.com");
        ResultSet resultSet = pst.executeQuery();

        Manager managerFromDatabase = new Manager();
        if (resultSet.next()) {
            mapResultSetToManager(resultSet, managerFromDatabase);
        }

        connection.close();

        assertManagersEqual(manager, managerFromDatabase);
    }

    @Test
    public void testFindPhoneNumberIds() throws Exception {
        DaoManager daoManager = getDaoManager();
        ManagerDao dao = daoManager.getDao(Manager.class);

        Connection connection = getDataSource().getConnection();
        Manager manager = new Manager();
        manager.setId(2);
        List<Integer> phoneNumberIds = dao.findPhoneNumberIds(manager);

        PreparedStatement pst = connection.prepareStatement("SELECT phoneNumber_id, manager_id " +
                "FROM Manager_PhoneNumber WHERE manager_id = ?");
        pst.setInt(1, manager.getId());
        ResultSet resultSet = pst.executeQuery();
        List<Integer> IdsFromDatabase = new ArrayList<>();

        while (resultSet.next())
            IdsFromDatabase.add(resultSet.getInt("phoneNumber_id"));

        resultSet.close();
        pst.close();
        connection.close();

        assertEquals(phoneNumberIds, IdsFromDatabase);
    }

    private void initManager(Manager manager) {
        manager.setFirstName("Almas");
        manager.setLastName("Akchabayev");
        manager.setEmail("almas.akchabayev@gmail.com");
        manager.setPassword("321");
        Company company = new Company();
        company.setId(1);
        manager.setCompany(company);
    }

    private void mapResultSetToManager(ResultSet resultSet, Manager managerFromDatabase) throws SQLException {
        managerFromDatabase.setId(resultSet.getInt("id"));
        managerFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
        managerFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
        managerFromDatabase.setFirstName(resultSet.getString("firstName"));
        managerFromDatabase.setLastName(resultSet.getString("lastName"));
        managerFromDatabase.setEmail(resultSet.getString("email"));
        managerFromDatabase.setPassword(resultSet.getString("password"));
        Company company = new Company();
        company.setId(resultSet.getInt("company"));
        managerFromDatabase.setCompany(company);
    }

    public void assertManagersEqual(Manager manager, Manager managerFromDatabase) {
        assertEquals(manager.getId(), managerFromDatabase.getId());
        assertEquals(manager.getUuid(), managerFromDatabase.getUuid());
        assertEquals(manager.isDeleted(), managerFromDatabase.isDeleted());
        assertEquals(manager.getFirstName(), managerFromDatabase.getFirstName());
        assertEquals(manager.getLastName(), managerFromDatabase.getLastName());
        assertEquals(manager.getEmail(), managerFromDatabase.getEmail());
        assertEquals(manager.getPassword(), managerFromDatabase.getPassword());
        if (manager.getCompany() != null)
            assertEquals(manager.getCompany().getId(), manager.getCompany().getId());
    }
}
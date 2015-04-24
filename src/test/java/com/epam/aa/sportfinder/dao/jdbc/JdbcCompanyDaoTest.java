package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.*;
import com.epam.aa.sportfinder.model.*;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class JdbcCompanyDaoTest extends TestConfig {


    @Test(expected = DaoException.class)
    public void testInsertFailsIfIdNotNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        CompanyDao dao = daoManager.getDao(Company.class);

        Company company = new Company();
        company.setId(1);

        dao.insert(company);
    }

    @Test(expected = DaoException.class)
    public void testInsertFailsIfUuidAlreadyExists() throws Exception {
        DaoManager daoManager = getDaoManager();
        CompanyDao dao = daoManager.getDao(Company.class);

        Company company = new Company();
        dao.insert(company);

        Company companyWithSameUuid = new Company();
        companyWithSameUuid.setUuid(company.getUuid());

        dao.insert(companyWithSameUuid);
    }

    @Test(expected = DaoException.class)
    public void testInsertFailsIfNameAlreadyExists() throws Exception {
        DaoManager daoManager = getDaoManager();
        CompanyDao dao = daoManager.getDao(Company.class);

        Company company = new Company();
        company.setName("UniqueCompany");
        dao.insert(company);

        Company companyWithSameName = new Company();
        companyWithSameName.setName(company.getName());

        dao.insert(companyWithSameName);
    }

    @Test
    public void testInsertSuccessWithoutIdAndUuid() throws Exception {
        DaoManager daoManager = getDaoManager();
        CompanyDao dao = daoManager.getDao(Company.class);

        Connection connection = getDataSource().getConnection();

        Company company = new Company();
        initCompany(company);

        dao.insert(company);

        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT id, uuid, deleted, name, address " +
                "FROM Company ORDER BY id DESC LIMIT 1");

        Company companyFromDatabase = new Company();
        if (resultSet.next()) {
            mapResultSetToCompany(resultSet, companyFromDatabase);
        }
        resultSet.close();
        st.close();
        connection.close();

        assertCompanysEqual(company, companyFromDatabase);
    }

    @Test
    public void testInsertPhoneNumbersSuccess() throws Exception {
        DaoManager daoManager = getDaoManager();
        CompanyDao dao = daoManager.getDao(Company.class);

        Connection connection = getDataSource().getConnection();

        Company company = new Company();
        initCompany(company);
        company.setId(1);
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        PhoneNumber phoneNumber1 = new PhoneNumber();
        phoneNumber1.setId(1);
        phoneNumbers.add(phoneNumber1);
        PhoneNumber phoneNumber2 = new PhoneNumber();
        phoneNumber2.setId(2);
        phoneNumbers.add(phoneNumber2);
        company.setPhoneNumbers(phoneNumbers);

        dao.insertPhoneNumbers(company);

        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT company_id, phoneNumber_id " +
                "FROM Company_PhoneNumber WHERE company_id = " + company.getId());

        ArrayList<Integer> phoneNumberIdsFromDatabase = new ArrayList<>();
        while (resultSet.next()) {
            phoneNumberIdsFromDatabase.add(resultSet.getInt("phoneNumber_id"));
        }
        resultSet.close();
        st.close();
        connection.close();

        List<PhoneNumber> phoneNumberList = company.getPhoneNumbers();
        assertEquals(phoneNumberList.size(), phoneNumberIdsFromDatabase.size());
        for (int i = 0; i < phoneNumberList.size(); i++) {
            assertEquals(phoneNumberList.get(i).getId(), phoneNumberIdsFromDatabase.get(i));
        }
    }


    @Test
    public void testAddPhoneNumberSuccess() throws Exception {
        DaoManager daoManager = getDaoManager();
        CompanyDao dao = daoManager.getDao(Company.class);

        Connection connection = getDataSource().getConnection();

        Company company = new Company();
        initCompany(company);
        company.setId(2);
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setId(1);

        dao.addPhoneNumber(company, phoneNumber);

        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT phoneNumber_id, company_id " +
                "FROM Company_PhoneNumber WHERE company_id = " + company.getId());


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
        CompanyDao dao = daoManager.getDao(Company.class);

        Company company = new Company();
        initCompany(company);

        dao.update(company);
    }

    @Test
    public void testUpdateSuccessIfIdNotNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        CompanyDao dao = daoManager.getDao(Company.class);

        Connection connection = getDataSource().getConnection();

        Company company = new Company();
        initCompany(company);
        company.setName("Other Name");
        company.setId(2);

        dao.update(company);

        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, name, address " +
                "FROM Company WHERE id = ?");
        pst.setInt(1, company.getId());
        ResultSet resultSet = pst.executeQuery();

        Company companyFromDatabase = new Company();
        if (resultSet.next()) {
            mapResultSetToCompany(resultSet, companyFromDatabase);
        }

        resultSet.close();
        pst.close();
        connection.close();

        assertCompanysEqual(company, companyFromDatabase);
    }

    @Test
    public void testDeleteInDbAndAssignTrueToObject() throws Exception {
        DaoManager daoManager = getDaoManager();
        CompanyDao dao = daoManager.getDao(Company.class);

        Connection connection = getDataSource().getConnection();

        Company company = new Company();
        company.setId(1);
        dao.delete(company);

        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, name, address " +
                "FROM Company WHERE id = ?");
        pst.setInt(1, company.getId());
        ResultSet resultSet = pst.executeQuery();
        Company companyFromDatabase = new Company();

        if (resultSet.next()) {
            mapResultSetToCompany(resultSet, companyFromDatabase);
        }
        resultSet.close();
        pst.close();
        connection.close();

        assertEquals(companyFromDatabase.isDeleted(), true);
        assertEquals(company.isDeleted(), companyFromDatabase.isDeleted());
    }

    @Test
    public void testRemovePhoneNumberSuccess() throws Exception {
        DaoManager daoManager = getDaoManager();
        CompanyDao dao = daoManager.getDao(Company.class);

        Connection connection = getDataSource().getConnection();

        Company company = new Company();
        initCompany(company);
        company.setId(2);
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setId(2);

        dao.removePhoneNumber(company, phoneNumber);

        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT company_id, phoneNumber_id " +
                "FROM Company_PhoneNumber WHERE company_id = " + company.getId());


        ArrayList<Integer> phoneNumberIdsFromDatabase = new ArrayList<>();
        while (resultSet.next()) {
            phoneNumberIdsFromDatabase.add(resultSet.getInt("phoneNumber_id"));
        }

        resultSet.close();
        st.close();
        connection.close();

        assertEquals(false, phoneNumberIdsFromDatabase.contains(2));
        List<PhoneNumber> phoneNumberList = company.getPhoneNumbers();
        assertEquals(phoneNumberList.size(), phoneNumberIdsFromDatabase.size());
        for (int i = 0; i < phoneNumberList.size(); i++) {
            assertEquals(phoneNumberList.get(i).getId(), phoneNumberIdsFromDatabase.get(i));
        }
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfIdIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        CompanyDao dao = daoManager.getDao(Company.class);

        Company dummycompany = new Company();
        Company company = dao.findById(dummycompany.getId());
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfIdIsNegative() throws Exception {
        DaoManager daoManager = getDaoManager();
        CompanyDao dao = daoManager.getDao(Company.class);

        Company company = dao.findById(-1);
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfElementCouldNotBeFounded() throws Exception {
        DaoManager daoManager = getDaoManager();
        CompanyDao dao = daoManager.getDao(Company.class);
        Company company = dao.findById(100000000);
    }

    @Test
    public void testFindByIdSuccessIfValidId() throws Exception {
        DaoManager daoManager = getDaoManager();
        CompanyDao dao = daoManager.getDao(Company.class);

        Connection connection = getDataSource().getConnection();
        Company company = dao.findById(1);

        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, name, address " +
                "FROM Company WHERE id = ?");
        pst.setInt(1, company.getId());
        ResultSet resultSet = pst.executeQuery();

        Company companyFromDatabase = new Company();
        if (resultSet.next()) {
            mapResultSetToCompany(resultSet, companyFromDatabase);
        }

        connection.close();

        assertCompanysEqual(company, companyFromDatabase);
    }

    @Test
    public void testFindPhoneNumberIds() throws Exception {
        DaoManager daoManager = getDaoManager();
        CompanyDao dao = daoManager.getDao(Company.class);

        Connection connection = getDataSource().getConnection();
        Company company = new Company();
        company.setId(2);
        List<Integer> phoneNumberIds = dao.findPhoneNumberIds(company);

        PreparedStatement pst = connection.prepareStatement("SELECT phoneNumber_id, company_id " +
                "FROM Company_PhoneNumber WHERE company_id = ?");
        pst.setInt(1, company.getId());
        ResultSet resultSet = pst.executeQuery();
        List<Integer> IdsFromDatabase = new ArrayList<>();

        while (resultSet.next())
            IdsFromDatabase.add(resultSet.getInt("phoneNumber_id"));

        resultSet.close();
        pst.close();
        connection.close();

        assertEquals(phoneNumberIds, IdsFromDatabase);
    }

    private void initCompany(Company company) {
        company.setName("Weekend Ltd");
        Address address = new Address();
        address.setId(1);
        company.setAddress(address);
    }

    private void mapResultSetToCompany(ResultSet resultSet, Company companyFromDatabase) throws SQLException {
        companyFromDatabase.setId(resultSet.getInt("id"));
        companyFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
        companyFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
        companyFromDatabase.setName(resultSet.getString("name"));
        Address address = new Address();
        address.setId(resultSet.getInt("address"));
        companyFromDatabase.setAddress(address);
    }

    public void assertCompanysEqual(Company company, Company companyFromDatabase) {
        assertEquals(company.getId(), companyFromDatabase.getId());
        assertEquals(company.getUuid(), companyFromDatabase.getUuid());
        assertEquals(company.isDeleted(), companyFromDatabase.isDeleted());
        assertEquals(company.getName(), companyFromDatabase.getName());
        if (company.getAddress() != null)
            assertEquals(company.getAddress().getId(), company.getAddress().getId());
    }
}
package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.*;
import com.epam.aa.sportfinder.model.*;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.After;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class JdbcCompanyDaoTest extends TestConfig {
    @Test(expected = DaoException.class)
    public void testInsertFailsIfIdNotNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            CompanyDao dao = daoManager.getDao(Company.class);
            Company company = new Company();
            company.setId(1);
            return dao.insert(company);
        });
    }

    @Test(expected = DaoException.class)
    public void testInsertFailsIfUuidAlreadyExists() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            CompanyDao dao = daoManager.getDao(Company.class);
            Company company = new Company();
            dao.insert(company);
            Company companyWithSameUuid = new Company();
            companyWithSameUuid.setUuid(company.getUuid());
            return dao.insert(companyWithSameUuid);
        });
    }

    @Test(expected = DaoException.class)
    public void testInsertFailsIfNameAlreadyExists() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            CompanyDao dao = daoManager.getDao(Company.class);

            Company company = new Company();
            company.setName("UniqueCompany");
            dao.insert(company);

            Company companyWithSameName = new Company();
            companyWithSameName.setName(company.getName());

            return dao.insert(companyWithSameName);
        });
    }

    @Test
    public void testInsertSuccessWithoutIdAndUuid() throws Exception {
        DaoManager daoManager = getDaoManager();
        Company company = new Company();
        daoManager.executeTx(daoManager1 -> {
            CompanyDao dao = daoManager.getDao(Company.class);
            initCompany(company);
            return dao.insert(company);
        });

        Connection connection = getDataSource().getConnection();
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

    @Test(expected = DaoException.class)
    public void testUpdateFailsIfIdIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            CompanyDao dao = daoManager.getDao(Company.class);

            Company company = new Company();
            initCompany(company);

            dao.update(company);
            return null;
        });
    }

    @Test
    public void testUpdateSuccessIfIdNotNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        Company company = new Company();
        daoManager.executeTx(daoManager1 -> {
            CompanyDao dao = daoManager.getDao(Company.class);

            initCompany(company);
            company.setName("Other Name");
            company.setId(2);

            dao.update(company);
            return null;
        });

        Connection connection = getDataSource().getConnection();
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
        Company company = new Company();
        daoManager.executeTx(daoManager1 -> {
            CompanyDao dao = daoManager.getDao(Company.class);
            company.setId(1);
            return dao.delete(company);
        });

        Connection connection = getDataSource().getConnection();
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

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfIdIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            CompanyDao dao = daoManager.getDao(Company.class);
            Company dummycompany = new Company();
            return dao.findById(dummycompany.getId());
        });
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfIdIsNegative() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            CompanyDao dao = daoManager.getDao(Company.class);
            return dao.findById(-1);
        });
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfElementCouldNotBeFounded() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            CompanyDao dao = daoManager.getDao(Company.class);
            return dao.findById(100000000);
        });
    }

    @Test
    public void testFindByIdSuccessIfValidId() throws Exception {
        DaoManager daoManager = getDaoManager();
        Company company = daoManager.executeTx(daoManager1 -> {
            CompanyDao dao = daoManager.getDao(Company.class);
            return dao.findById(1);
        });

        Connection connection = getDataSource().getConnection();
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
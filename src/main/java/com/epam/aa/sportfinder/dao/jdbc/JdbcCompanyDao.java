package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.CompanyDao;
import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.model.Company;
import com.epam.aa.sportfinder.model.PhoneNumber;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcCompanyDao extends JdbcBaseDao<Company> implements CompanyDao {
    public JdbcCompanyDao(Connection connection) {
        super(connection);
    }

    @Override
    public Company insertPhoneNumbers(Company company) throws DaoException {
        if (company.getPhoneNumbers() == null)
            throw new DaoException("List of PhoneNumbers is null, cannot be inserted");

        StringBuffer insertPhoneNumbersBuffer = new StringBuffer("INSERT INTO Company_PhoneNumber " +
                "(company_id, phoneNumber_id) VALUES ");

        Integer companyId = company.getId();
        String prefix = "";
        for (PhoneNumber phoneNumber : company.getPhoneNumbers()) {
            insertPhoneNumbersBuffer.append(prefix);
            prefix = ", ";
            insertPhoneNumbersBuffer.append("(");
            insertPhoneNumbersBuffer.append(companyId);
            insertPhoneNumbersBuffer.append(", ");
            insertPhoneNumbersBuffer.append(phoneNumber.getId());
            insertPhoneNumbersBuffer.append(")");
        }
        String sql = insertPhoneNumbersBuffer.toString();

        try (Statement st = getConnection().createStatement()){
            st.execute(sql);
        } catch (SQLException e) {
            throw new DaoException("Insertion failed", e);
        }
        return company;
    }

    @Override
    public Company removePhoneNumber(Company company, PhoneNumber phoneNumber) throws DaoException {
        if(company.getId() == null || phoneNumber.getId() == null)
            throw new DaoException("Could not remove phoneNumber, id is null");

        if (company.getPhoneNumbers() == null)
            throw new DaoException("List of PhoneNumbers is null, cannot be removed");

        String sql = "DELETE FROM Company_PhoneNumber WHERE company_id = " + company.getId() +
                " AND phoneNumber_id = " + phoneNumber.getId() + ";";
        try (Statement st = getConnection().createStatement()){
            st.execute(sql);
        } catch (SQLException e) {
            throw new DaoException("Removal failed", e);
        }
        if (company.containsPhoneNumber(phoneNumber))
            company.removePhoneNumber(phoneNumber);
        return company;
    }

    @Override
    public Company addPhoneNumber(Company company, PhoneNumber phoneNumber) throws DaoException {
        if(company.getId() == null || phoneNumber.getId() == null)
            throw new DaoException("Could not add phoneNumber, id is null");

        String sql = "INSERT INTO Company_PhoneNumber " +
                "(phoneNumber_id, company_id) VALUES (" +
                phoneNumber.getId() + ", " +company.getId() +");";

        try (Statement st = getConnection().createStatement()){
            st.execute(sql);
        } catch (SQLException e) {
            throw new DaoException("Insertion failed", e);
        }

        if (company.getPhoneNumbers() == null)
            company.setPhoneNumbers(new ArrayList<>());

        if (!company.containsPhoneNumber(phoneNumber))
            company.addPhoneNumber(phoneNumber);
        return company;
    }

    @Override
    public List<Integer> findPhoneNumberIds(Company company) throws DaoException {
        Integer companyId = company.getId();
        if (companyId == null)
            throw new DaoException("Could not find phoneNumbers, company id is null");

        String sql = "SELECT company_id, phoneNumber_id FROM Company_PhoneNumber " +
                "WHERE company_id = " + companyId;

        List<Integer> phoneNumberIds = new ArrayList<>();
        try (Statement st = getConnection().createStatement()) {
            try (ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    phoneNumberIds.add(rs.getInt("phoneNumber_id"));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot find corresponding phoneNumbers", e);
        }
        return phoneNumberIds;
    }
}

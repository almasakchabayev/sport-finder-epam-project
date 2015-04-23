package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.ManagerDao;
import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.model.Company;
import com.epam.aa.sportfinder.model.Manager;
import com.epam.aa.sportfinder.model.PhoneNumber;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcManagerDao extends JdbcBaseDao<Manager> implements ManagerDao {
    public JdbcManagerDao(Connection connection) {
        super(connection);
    }

    @Override
    public Manager insertPhoneNumbers(Manager manager) throws DaoException {
        if (manager.getPhoneNumbers() == null)
            throw new DaoException("List of PhoneNumbers is null, cannot be inserted");

        StringBuffer insertPhoneNumbersBuffer = new StringBuffer("INSERT INTO Manager_PhoneNumber " +
                "(manager_id, phoneNumber_id) VALUES ");

        Integer managerId = manager.getId();
        String prefix = "";
        for (PhoneNumber phoneNumber : manager.getPhoneNumbers()) {
            insertPhoneNumbersBuffer.append(prefix);
            prefix = ", ";
            insertPhoneNumbersBuffer.append("(");
            insertPhoneNumbersBuffer.append(managerId);
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
        return manager;
    }

    @Override
    public Manager removePhoneNumber(Manager manager, PhoneNumber phoneNumber) throws DaoException {
        if(manager.getId() == null || phoneNumber.getId() == null)
            throw new DaoException("Could not remove phoneNumber, id is null");

        if (manager.getPhoneNumbers() == null)
            throw new DaoException("List of PhoneNumbers is null, cannot be removed");

        String sql = "DELETE FROM Manager_PhoneNumber WHERE manager_id = " + manager.getId() +
                " AND phoneNumber_id = " + phoneNumber.getId() + ";";
        try (Statement st = getConnection().createStatement()){
            st.execute(sql);
        } catch (SQLException e) {
            throw new DaoException("Removal failed", e);
        }
        if (manager.containsPhoneNumber(phoneNumber))
            manager.removePhoneNumber(phoneNumber);
        return manager;
    }

    @Override
    public Manager addPhoneNumber(Manager manager, PhoneNumber phoneNumber) throws DaoException {
        if(manager.getId() == null || phoneNumber.getId() == null)
            throw new DaoException("Could not add phoneNumber, id is null");

        String sql = "INSERT INTO Manager_PhoneNumber " +
                "(phoneNumber_id, manager_id) VALUES (" +
                phoneNumber.getId() + ", " +manager.getId() +");";

        try (Statement st = getConnection().createStatement()){
            st.execute(sql);
        } catch (SQLException e) {
            throw new DaoException("Insertion failed", e);
        }

        if (manager.getPhoneNumbers() == null)
            manager.setPhoneNumbers(new ArrayList<>());

        if (!manager.containsPhoneNumber(phoneNumber))
            manager.addPhoneNumber(phoneNumber);
        return manager;
    }

    @Override
    public List<Integer> findPhoneNumberIds(Manager manager) throws DaoException {
        Integer managerId = manager.getId();
        if (managerId == null)
            throw new DaoException("Could not find phoneNumbers, manager id is null");

        String sql = "SELECT manager_id, phoneNumber_id FROM Manager_PhoneNumber " +
                "WHERE manager_id = " + managerId;

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

    @Override
    public Manager findByEmail(String email) throws DaoException {
        String sql = "SELECT id, uuid, deleted, firstName, lastName, " +
                "email, password, company " +
                "FROM Manager WHERE email = ?";

        Manager manager = null;
        try (PreparedStatement pst = getConnection().prepareStatement(sql)) {
            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    manager = new Manager();
                    manager.setId(rs.getInt("id"));
                    manager.setUuid((UUID) rs.getObject("uuid"));
                    manager.setDeleted(rs.getBoolean("deleted"));
                    manager.setFirstName(rs.getString("firstName"));
                    manager.setLastName(rs.getString("lastName"));
                    manager.setEmail(rs.getString("email"));
                    manager.setPassword(rs.getString("password"));
                    Company company = new Company();
                    company.setId(rs.getInt("company"));
                    manager.setCompany(company);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot find corresponding phoneNumbers", e);
        }
        return manager;
    }
}

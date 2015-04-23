package com.epam.aa.sportfinder.dao;

import com.epam.aa.sportfinder.model.Manager;
import com.epam.aa.sportfinder.model.PhoneNumber;

import java.util.List;

public interface ManagerDao extends GenericDao<Manager> {
    Manager insertPhoneNumbers(Manager manager) throws DaoException;
    Manager removePhoneNumber(Manager manager, PhoneNumber phoneNumber) throws DaoException;
    Manager addPhoneNumber(Manager manager, PhoneNumber phoneNumber) throws DaoException;
    List<Integer> findPhoneNumberIds(Manager manager) throws DaoException;

    Manager findByEmail(String email) throws DaoException;
}

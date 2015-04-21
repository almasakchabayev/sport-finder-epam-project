package com.epam.aa.sportfinder.dao;

import com.epam.aa.sportfinder.model.Company;
import com.epam.aa.sportfinder.model.PhoneNumber;

import java.util.List;

public interface CompanyDao extends GenericDao<Company> {
    Company insertPhoneNumbers(Company company) throws DaoException;
    Company removePhoneNumber(Company company, PhoneNumber phoneNumber) throws DaoException;
    Company addPhoneNumber(Company company, PhoneNumber phoneNumber) throws DaoException;
    List<Integer> findPhoneNumberIds(Company company) throws DaoException;
}
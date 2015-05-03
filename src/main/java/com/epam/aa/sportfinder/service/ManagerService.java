package com.epam.aa.sportfinder.service;

import com.epam.aa.sportfinder.dao.*;
import com.epam.aa.sportfinder.model.Address;
import com.epam.aa.sportfinder.model.Company;
import com.epam.aa.sportfinder.model.Manager;
import com.epam.aa.sportfinder.model.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ManagerService extends BaseService{
    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerService.class);

    public static Manager findByCredentials(String email, String password) {
        DaoManager daoManager = createDaoManager();

        return daoManager.executeTx(manager -> {
            ManagerDao managerDao = manager.getDao(Manager.class);
            CompanyDao companyDao = manager.getDao(Company.class);
            PhoneNumberDao phoneNumberDao = manager.getDao(PhoneNumber.class);
            Manager result = null;
            try {
                result = managerDao.findByEmail(email);
                if (result == null)
                    return null;

                result.setCompany(companyDao.findById(result.getCompany().getId()));
                List<Integer> phoneNumberIds = managerDao.findPhoneNumberIds(result);
                if (phoneNumberIds != null) {
                    List<PhoneNumber> phoneNumbers = phoneNumberDao.findByIds(phoneNumberIds);
                    result.setPhoneNumbers(phoneNumbers);
                }

                if (!result.getPassword().equals(password))
                    result = null;
            } catch (DaoException e) {
                LOGGER.warn("Could not properly fetch manager by email {}", email, e);
            }
            return result;
        });
    }

    public static Manager create(Manager manager) {
        DaoManager daoManager = createDaoManager();

        return daoManager.executeTx(thisDaoManager -> {
            ManagerDao managerDao = thisDaoManager.getDao(Manager.class);
            CompanyDao companyDao = thisDaoManager.getDao(Company.class);
            PhoneNumberDao phoneNumberDao = thisDaoManager.getDao(PhoneNumber.class);
            AddressDao addressDao = thisDaoManager.getDao(Address.class);

            Address address = addressDao.insert(manager.getCompany().getAddress());
            Company company;
            try {
                company = companyDao.insert(manager.getCompany());
            } catch (DaoException e) {
                throw new ServiceException("Such company name already exists", e);
            }
            company.setAddress(address);
            manager.setCompany(company);

            List<PhoneNumber> phoneNumbers = new ArrayList<>();
            //TODO: create bulk insert for PhoneNumbers
            for (PhoneNumber phoneNumber : manager.getPhoneNumbers()) {
                PhoneNumber insert = phoneNumberDao.insert(phoneNumber);
                phoneNumbers.add(insert);
            }
            manager.setPhoneNumbers(phoneNumbers);
            try {
                managerDao.insert(manager);
            } catch (DaoException e) {
                throw new ServiceException("Such email already exists", e);
            }
            managerDao.insertPhoneNumbers(manager);
            return manager;
        });
    }
}

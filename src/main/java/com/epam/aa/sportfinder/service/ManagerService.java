package com.epam.aa.sportfinder.service;

import com.epam.aa.sportfinder.dao.*;
import com.epam.aa.sportfinder.model.Company;
import com.epam.aa.sportfinder.model.Manager;
import com.epam.aa.sportfinder.model.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ManagerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerService.class);

    public static Manager findByCredentials(String email, String password) {
        DaoFactory daoFactory = DaoFactory.getInstance();
        DaoManager daoManager = daoFactory.createDaoManager();

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
}

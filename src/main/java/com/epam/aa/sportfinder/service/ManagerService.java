package com.epam.aa.sportfinder.service;

import com.epam.aa.sportfinder.dao.*;
import com.epam.aa.sportfinder.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ManagerService extends BaseService{
    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerService.class);

    public static Manager findByCredentials(String email, String password) {
        DaoManager daoManager = createDaoManager();

        return daoManager.executeTx(m -> {
            ManagerDao managerDao = m.getDao(Manager.class);
            AddressDao addressDao = m.getDao(Address.class);
            CompanyDao companyDao = m.getDao(Company.class);
            PhoneNumberDao phoneNumberDao = m.getDao(PhoneNumber.class);
            ImageDao imageDao = m.getDao(Image.class);
            Manager result = null;
            try {
                result = managerDao.findByEmail(email);
                if (result == null)
                    return null;

                Company company = companyDao.findById(result.getCompany().getId());
                Address address = addressDao.findById(company.getAddress().getId());
                company.setAddress(address);
                result.setCompany(company);
                if (result.getImage() != null && result.getImage().getId() != null) {
                    result.setImage(imageDao.findById(result.getImage().getId()));
                }
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

        return daoManager.executeTx(m -> {
            ManagerDao managerDao = m.getDao(Manager.class);
            CompanyDao companyDao = m.getDao(Company.class);
            PhoneNumberDao phoneNumberDao = m.getDao(PhoneNumber.class);
            AddressDao addressDao = m.getDao(Address.class);
            ImageDao imageDao = m.getDao(Image.class);

            Address address = addressDao.insert(manager.getCompany().getAddress());
            Company company;
            try {
                company = companyDao.insert(manager.getCompany());
            } catch (DaoException e) {
                throw new ServiceException("Such company name already exists", e);
            }
            company.setAddress(address);
            manager.setCompany(company);

            Image image = imageDao.insert(manager.getImage());
            manager.setImage(image);

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

    public static void update(Manager manager) {
        DaoManager daoManager = createDaoManager();

        daoManager.executeTx(m -> {
            ManagerDao managerDao = m.getDao(Manager.class);
            CompanyDao companyDao = m.getDao(Company.class);
            PhoneNumberDao phoneNumberDao = m.getDao(PhoneNumber.class);
            AddressDao addressDao = m.getDao(Address.class);
            ImageDao imageDao = m.getDao(Image.class);

            addressDao.update(manager.getCompany().getAddress());
            try {
                companyDao.update(manager.getCompany());
            } catch (DaoException e) {
                throw new ServiceException("Such company name already exists", e);
            }

            if (manager.getImage() != null && manager.getImage().getId() != null) {
                imageDao.update(manager.getImage());
            } else if (manager.getImage() != null && manager.getImage().getId() == null) {
                Image image = imageDao.insert(manager.getImage());
                manager.setImage(image);
            }


            PhoneNumber newPhoneNumber = null;
            for (PhoneNumber phoneNumber : manager.getPhoneNumbers()) {
                if (phoneNumberDao.findById(phoneNumber.getId()) != null) {
                    phoneNumberDao.update(phoneNumber);
                } else {
                    newPhoneNumber = phoneNumberDao.insert(phoneNumber);
                }
            }
            if (newPhoneNumber != null) {
                manager.addPhoneNumber(newPhoneNumber);
            }
            try {
                managerDao.update(manager);
            } catch (DaoException e) {
                throw new ServiceException("Such email already exists", e);
            }
            return null;
        });
    }
}

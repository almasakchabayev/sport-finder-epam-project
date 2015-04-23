package com.epam.aa.sportfinder.dao;

import com.epam.aa.sportfinder.model.PhoneNumber;

import java.util.List;

public interface PhoneNumberDao extends GenericDao<PhoneNumber> {
    List<PhoneNumber> findByIds(List<Integer> phoneNumberIds) throws DaoException;
}

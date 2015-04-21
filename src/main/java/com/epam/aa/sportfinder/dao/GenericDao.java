package com.epam.aa.sportfinder.dao;

import com.epam.aa.sportfinder.model.BaseEntity;

public interface GenericDao<T extends BaseEntity> {
    //TODO: throw Exception explicitly
    T findById(Integer id) throws DaoException;
    T insert(T t) throws DaoException;
    void update(T t) throws DaoException;
    T delete(T t) throws DaoException;
}

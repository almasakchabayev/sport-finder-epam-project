package com.epam.aa.sportfinder.dao;

import com.epam.aa.sportfinder.model.BaseEntity;

public interface DaoManager {
    <T> T execute(DaoCommand<T> daoCommand) throws DaoException;
    <T> T transaction(DaoCommand<T> daoCommand) throws DaoException;
    default <T> T executeTx(DaoCommand<T> daoCommand) throws DaoException {
        return execute(daoManager -> daoManager.transaction(daoCommand));
    }

    <T extends GenericDao> T getDao(Class<? extends BaseEntity> clazz) throws DaoException;
}
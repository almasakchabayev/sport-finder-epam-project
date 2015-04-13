package com.epam.aa.sportfinder.dao;

public interface DaoManager {
    <T> T execute(DaoCommand<T> daoCommand);
    <T> T transaction(DaoCommand<T> daoCommand);
    default <T> T executeTx(DaoCommand<T> daoCommand) {
        return execute(daoManager -> daoManager.transaction(daoCommand));
    }

    // add more Daos here
    AddressDao getAddressDao();
}
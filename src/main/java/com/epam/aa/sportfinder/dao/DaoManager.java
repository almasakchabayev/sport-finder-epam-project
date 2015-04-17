package com.epam.aa.sportfinder.dao;

import com.epam.aa.sportfinder.model.BaseEntity;

public interface DaoManager {
    <T> T execute(DaoCommand<T> daoCommand);
    <T> T transaction(DaoCommand<T> daoCommand);
    default <T> T executeTx(DaoCommand<T> daoCommand) {
        return execute(daoManager -> daoManager.transaction(daoCommand));
    }

    // add more Daos here
//    AddressDao getAddressDao();
//    FloorCoverageDao getFloorCoverageDao();
//    SportDao getSportDao();

    // TODO:generic getter
    <T extends GenericDao> T getDao(Class<? extends BaseEntity> clazz);

}
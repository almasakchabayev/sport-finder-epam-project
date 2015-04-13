package com.epam.aa.sportfinder.dao;

public interface DaoCommand<T> {
    T execute(DaoManager daoManager);
}

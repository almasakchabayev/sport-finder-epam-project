package com.epam.aa.sportfinder.dao;

import com.epam.aa.sportfinder.model.BaseEntity;

public interface GenericDao<T extends BaseEntity> {
    //TODO: throw Exception explicitly
    T findById(Integer id);
    void insert(T t);
    void update(T t);
    void delete(T t);
}

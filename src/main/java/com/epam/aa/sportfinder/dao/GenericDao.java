package com.epam.aa.sportfinder.dao;

import com.epam.aa.sportfinder.model.BaseEntity;

public interface GenericDao<T extends BaseEntity> {
    //TODO: throw Exception explicitly
    T findById(Integer id);
    T insert(T t);
    void update(T t);
    T delete(T t);
}

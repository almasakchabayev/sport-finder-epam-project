package com.epam.aa.sportfinder.dao;

import com.epam.aa.sportfinder.model.BaseEntity;

public interface GenericDao<T extends BaseEntity> {
    T find(Integer id);
    void create(T entity);
    void update(T entity);
    void delete(T entity);
}

package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.model.BaseEntity;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;


public class QueryFactory {

    public static <T extends BaseEntity> Query getQuery(T entity, int type) {
        Query query;
        switch (type) {
            case 1 : query = getInsertQuery(entity);
            default: throw new DaoException("The Query type is invalid");
        }
    }
    private static <T extends BaseEntity> Query getInsertQuery(T entity) {
        PropertyDescriptor[] propertyDescriptors = null;
        try {
            propertyDescriptors = Introspector.getBeanInfo(entity.getClass()).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new DaoException(e);
        }

        if (propertyDescriptors.length == 0) {
            throw new DaoException("Entity does not have any bean properties");
        }
        Query query = new Query(Query.INSERT);
        try {
            for (PropertyDescriptor pd : propertyDescriptors) {
                if (pd.getReadMethod() != null && !"class".equals(pd.getName())) {
                    if (pd.getReadMethod().invoke(entity) != null) {
                        query.addPropertyDescriptor(pd);
                    }
                }
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new DaoException(e);
        }

        return query;
    }
}
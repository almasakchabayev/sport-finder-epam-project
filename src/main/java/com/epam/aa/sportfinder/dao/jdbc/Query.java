package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.model.BaseEntity;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Query {
    public static final int INSERT = 1;
    public static final int UPDATE = 2;
    public static final int DELETE = 3;
    public static final int FIND = 4;

    private List<PropertyDescriptor> propertyDescriptors;
    private Class<? extends BaseEntity> clazz;
    private int type;

    public Query(int type) {
        this.propertyDescriptors = new ArrayList<>();
        this.type = type;
    }

    public static <T extends BaseEntity> Query getQuery(T entity, int type) {
        PropertyDescriptor[] propertyDescriptors;
        try {
            propertyDescriptors = Introspector.getBeanInfo(entity.getClass()).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new DaoException(e);
        }

        if (propertyDescriptors.length == 0) {
            throw new DaoException("Entity does not have any bean properties");
        }
        Query query = new Query(type);
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
        query.setClazz(entity.getClass());

        return query;
    }


    public String getSqlQuery() {
        if (type == INSERT) return getInsertSqlQuery();
        if (type == UPDATE) return getUpdateSqlQuery();
        // cannot happen
        throw new DaoException("Query has no type");
    }

    public String getInsertSqlQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("INSERT INTO ");
        queryBuilder.append(clazz.getSimpleName());
        queryBuilder.append("(");

        StringBuilder valuesBuilder = new StringBuilder();
        valuesBuilder.append("VALUES(");
        String prefix = "";
        for (PropertyDescriptor pd : propertyDescriptors) {
            queryBuilder.append(prefix);
            valuesBuilder.append(prefix);
            prefix = ", ";
            queryBuilder.append(pd.getName());
            valuesBuilder.append("?");
        }
        queryBuilder.append(") ");
        valuesBuilder.append(")");
        queryBuilder.append(valuesBuilder.toString());
        return queryBuilder.toString();
    }

    public String getUpdateSqlQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("UPDATE ");
        queryBuilder.append(clazz.getSimpleName());
        queryBuilder.append(" SET ");

        String prefix = "";
        for (PropertyDescriptor pd : propertyDescriptors) {
            queryBuilder.append(prefix);
            prefix = ", ";
            queryBuilder.append(pd.getName());
            queryBuilder.append(" = ?");
        }
        queryBuilder.append("  WHERE id = ?");
        return queryBuilder.toString();
    }

    public void addPropertyDescriptor(PropertyDescriptor pd) {
        propertyDescriptors.add(pd);
    }

    public void setClazz(Class<? extends BaseEntity> clazz) {
        this.clazz = clazz;
    }

    public List<PropertyDescriptor> getPropertyDescriptors() {
        return propertyDescriptors;
    }

}

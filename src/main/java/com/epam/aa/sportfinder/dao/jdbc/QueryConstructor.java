package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.model.Address;
import com.epam.aa.sportfinder.model.BaseEntity;
import com.epam.aa.sportfinder.model.SportPlace;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class QueryConstructor {
    private List<PropertyDescriptor> propertyDescriptors;
    private Class<? extends BaseEntity> clazz;

    private QueryConstructor() {
        this.propertyDescriptors = new ArrayList<>();
    }

    public static <T extends BaseEntity> QueryConstructor createQueryConstructor(T entity) {
        PropertyDescriptor[] propertyDescriptors;
        try {
            propertyDescriptors = Introspector.getBeanInfo(entity.getClass()).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new DaoException(e);
        }

        if (propertyDescriptors.length == 0) {
            throw new DaoException("Entity does not have any bean properties");
        }
        QueryConstructor queryConstructor = new QueryConstructor();
        try {
            for (PropertyDescriptor pd : propertyDescriptors) {
                if (pd.getReadMethod() != null && !"class".equals(pd.getName())) {
                    Object invoke = pd.getReadMethod().invoke(entity);
                    if (invoke != null && !(invoke instanceof BaseEntity)) {
                        queryConstructor.addPropertyDescriptor(pd);
                    }
                }
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new DaoException(e);
        }
        queryConstructor.setClazz(entity.getClass());

        return queryConstructor;
    }

    public static <T extends BaseEntity> String getDeleteQuery(T entity) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("UPDATE ");
        queryBuilder.append(entity.getClass().getSimpleName());
        queryBuilder.append(" SET deleted = TRUE WHERE id = ?");
        return queryBuilder.toString();
    }

    //TODO: is it possible to do move find method from Daos to JdbcAbstractDao with this method?
    public static String getFindByIdQuery(Class<? extends BaseEntity> clazz) {
        PropertyDescriptor[] propertyDescriptors;
        try {
            propertyDescriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new DaoException(e);
        }

        if (propertyDescriptors.length == 0) {
            throw new DaoException("Entity does not have any bean properties");
        }

        List<PropertyDescriptor> pdsForUse = new ArrayList<>();
        for (PropertyDescriptor pd : propertyDescriptors) {
            if (pd.getReadMethod() != null && !"class".equals(pd.getName())) {
                pdsForUse.add(pd);
            }
        }
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT ");

        String prefix = "";
        for (PropertyDescriptor pd : pdsForUse) {
            queryBuilder.append(prefix);
            prefix = ", ";
            queryBuilder.append(pd.getName());
        }
        queryBuilder.append(" FROM ");
        queryBuilder.append(clazz.getSimpleName());
        queryBuilder.append(" WHERE id = ?");
        return queryBuilder.toString();
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
            if (pd.getName().equals("id")) continue;
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

    public static void main(String[] args) {
        SportPlace sportPlace = new SportPlace();
        sportPlace.setId(1);
        sportPlace.setCapacity(10);
        sportPlace.setChangingRoom(true);
        sportPlace.setDescription("Awesome sportplace");
        sportPlace.setIndoor(true);
        sportPlace.setAddress(new Address());

        QueryConstructor queryConstructor = QueryConstructor.createQueryConstructor(sportPlace);
        System.out.println(queryConstructor.getInsertSqlQuery());
    }
}

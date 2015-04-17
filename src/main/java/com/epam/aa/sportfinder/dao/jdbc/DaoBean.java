package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.model.BaseEntity;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaoBean {
    private static Map<Class<? extends BaseEntity>, DaoBean> queries = new HashMap<>();

    private final List<PropertyDescriptor> propertyDescriptors = new ArrayList<>();
    private final Class<? extends BaseEntity> clazz;

    private DaoBean(Class<? extends BaseEntity> clazz) {
        this.clazz = clazz;
    }

    public static <T extends BaseEntity> DaoBean getDaoBean(Class<T> clazz) {
        DaoBean daoBean = queries.get(clazz);
        if(daoBean != null) {
            return daoBean;
        }

        daoBean = new DaoBean(clazz);

        PropertyDescriptor[] propertyDescriptors;
        try {
            propertyDescriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new DaoException(e);
        }

        if (propertyDescriptors.length == 0) {
            throw new DaoException("Entity does not have any bean properties");
        }

        for (PropertyDescriptor pd : propertyDescriptors) {
            if (!"class".equals(pd.getName()) && pd.getReadMethod().getAnnotation(Transient.class) == null) {
                daoBean.addToPropertyDescriptors(pd);
            }
        }
        queries.put(clazz, daoBean);
        return daoBean;
    }

    private void addToPropertyDescriptors(PropertyDescriptor pd) {
        propertyDescriptors.add(pd);
    }

    public List<PropertyDescriptor> getPropertyDescriptors() {
        return propertyDescriptors;
    }

    public List<PropertyDescriptor> getPropertyDescriptorsWithoutId() {
        List<PropertyDescriptor> result = new ArrayList<>();
        result.addAll(propertyDescriptors);
        PropertyDescriptor idPropertyDescriptor;
        try {
            idPropertyDescriptor = new PropertyDescriptor("id", clazz);
        } catch (IntrospectionException e) {
            throw new DaoException("Could not instantiate id field PropertyDescriptor for " + clazz, e);
        }
        result.remove(idPropertyDescriptor);
        return result;
    }


    public Class<? extends BaseEntity> getClazz() {
        return clazz;
    }
}

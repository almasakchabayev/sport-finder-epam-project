package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.model.BaseEntity;
import com.google.common.collect.HashBasedTable;

import java.beans.PropertyDescriptor;

public class Query {
    private static HashBasedTable<Class<? extends BaseEntity>, Type, Query> queries = HashBasedTable.create();;

    private final DaoBean daoBean;
    private final Type type;
    private final String queryString;

    private Query(DaoBean daoBean, Type type) {
        this.daoBean = daoBean;
        this.type = type;
        this.queryString = constructQueryString();
    }

    public DaoBean getDaoBean() {
        return daoBean;
    }

    public Type getType() {
        return type;
    }

    public String getQueryString() {
        return queryString;
    }

    public static <T extends BaseEntity> Query getQuery(Class<T> clazz, Type type) {
        Query query = queries.get(clazz, type);
        if (query != null)
            return query;

        DaoBean daoBean = DaoBean.getDaoBean(clazz);
        query = new Query(daoBean, type);
        return query;
    }

    private String constructQueryString() {
        String result = null;
        if (type.equals(Type.DELETE))
            result = constructDeleteQuery();

        if (type.equals(Type.INSERT))
            result = constructInsertSqlQuery();

        if (type.equals(Type.UPDATE))
            result = constructUpdateSqlQuery();

        if (type.equals(Type.FIND_BY_ID))
            result = constructFindByIdQuery();

        return result;
    }

    private String constructDeleteQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("UPDATE ");
        queryBuilder.append(daoBean.getClazz().getSimpleName());
        queryBuilder.append(" SET deleted = TRUE WHERE id = ?");
        return queryBuilder.toString();
    }


    private String constructInsertSqlQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("INSERT INTO ");
        queryBuilder.append(daoBean.getClazz().getSimpleName());
        queryBuilder.append("(");

        StringBuilder valuesBuilder = new StringBuilder();
        valuesBuilder.append("VALUES(");

        String prefix = "";
        for (PropertyDescriptor pd : daoBean.getPropertyDescriptorsWithoutId()) {
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

    private String constructUpdateSqlQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("UPDATE ");
        queryBuilder.append(daoBean.getClazz().getSimpleName());
        queryBuilder.append(" SET ");

        String prefix = "";
        for (PropertyDescriptor pd : daoBean.getPropertyDescriptors()) {
            if (pd.getName().equals("id")) continue;
            queryBuilder.append(prefix);
            prefix = ", ";
            queryBuilder.append(pd.getName());
            queryBuilder.append(" = ?");
        }
        queryBuilder.append("  WHERE id = ?");
        return queryBuilder.toString();
    }

    public String constructFindByIdQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT ");

        String prefix = "";
        for (PropertyDescriptor pd : daoBean.getPropertyDescriptors()) {
            queryBuilder.append(prefix);
            prefix = ", ";
            queryBuilder.append(pd.getName());
        }
        queryBuilder.append(" FROM ");
        queryBuilder.append(daoBean.getClazz().getSimpleName());
        queryBuilder.append(" WHERE deleted = FALSE AND id = ?");
        return queryBuilder.toString();
    }

    private static void addQuery(Class<? extends BaseEntity> clazz, Type type, Query query) {
        queries.put(clazz, type, query);
    }


    public enum Type {
        INSERT, UPDATE, DELETE, FIND_BY_ID
    }
}

package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.GenericDao;
import com.epam.aa.sportfinder.model.BaseEntity;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.List;

public abstract class JdbcBaseDao<T extends BaseEntity> implements GenericDao<T> {
    private final Connection connection;
    private final Class<T> entityClass;

    protected JdbcBaseDao(Connection connection) {
        this.connection = connection;
        this.entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected Connection getConnection() {
        return connection;
    }

    @Override
    public T findById(Integer id) throws DaoException{
        if (id == null)
            throw new DaoException("Could not find element by id, id is null", new NullPointerException());
        if (id < 1)
            throw new DaoException(new IllegalArgumentException("Could not find element by id, id cannot be less than 1"));

        T result;
        Query query = Query.getQuery(entityClass, Query.Type.FIND_BY_ID);
        try (PreparedStatement pst = getConnection().prepareStatement(query.getQueryString())){
            pst.setObject(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) result = map(rs, query.getDaoBean());
                else throw new DaoException("Could not find element by id " + id);
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find element by id " + id, e);
        }
        return result;
    }

    @Override
    public T insert(T entity) throws DaoException {
        if (entity.getId() != null) throw new DaoException("Insertion failed, id is not null");

        Query query = Query.getQuery(entity.getClass(), Query.Type.INSERT);
        try (PreparedStatement pst = getConnection().prepareStatement(query.getQueryString(),
                Statement.RETURN_GENERATED_KEYS)){


            List<PropertyDescriptor> pds = query.getDaoBean().getPropertyDescriptorsWithoutId();


            for (PropertyDescriptor pd : pds) {
                int parameterIndex = pds.indexOf(pd) + 1;
                fillPreparedStatement(entity, pst, pd, parameterIndex);
            }

            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) entity.setId(rs.getInt(1));
                else throw new DaoException("Id was not generated");
            }
        } catch (SQLException | IllegalAccessException | InvocationTargetException e) {
            throw new DaoException("Insertion failed", e);
        }
        return entity;
    }

    @Override
    public void update(T entity) throws DaoException{
        if (entity.getId() == null)
            throw new DaoException("Update failed, id is null", new NullPointerException());

        Query query = Query.getQuery(entity.getClass(), Query.Type.UPDATE);
        try (PreparedStatement pst = getConnection().prepareStatement(query.getQueryString())){

            List<PropertyDescriptor> pds = query.getDaoBean().getPropertyDescriptorsWithoutId();

            int count = 1;
            for (PropertyDescriptor pd : pds) {
                int parameterIndex = pds.indexOf(pd) + 1;
                fillPreparedStatement(entity, pst, pd, parameterIndex);
                count++;
            }
            pst.setInt(count, entity.getId());

            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                throw new DaoException("Update failed, no rows affected.");
            }
        } catch (SQLException | IllegalAccessException | InvocationTargetException e) {
            throw new DaoException("Updating failed", e);
        }
    }

    @Override
    public T delete(T entity) throws DaoException {
        if (entity.getId() == null)
            throw new DaoException("Deletion failed, id is null", new NullPointerException());
        Query query = Query.getQuery(entity.getClass(), Query.Type.DELETE);
        try (PreparedStatement pst = getConnection().prepareStatement(query.getQueryString())){
            pst.setInt(1, entity.getId());

            int affectedRows = pst.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Deletion failed, no rows affected.");
            }
            entity.setDeleted(true);
        } catch (SQLException e) {
            throw new DaoException("Deletion failed", e);
        }
        return entity;
    }

    private T map(ResultSet resultSet, DaoBean daoBean) {
        T result;
        try {
            result = entityClass.newInstance();
            for (PropertyDescriptor pd : daoBean.getPropertyDescriptors()) {
                pd.getWriteMethod().invoke(result, resultSet.getObject(pd.getName()));
            }
        } catch (InstantiationException | IllegalAccessException | SQLException | InvocationTargetException e) {
            throw new DaoException("Could not assign properties from db to object", e);
        }
        return result;
    }

    protected void fillPreparedStatement(T entity, PreparedStatement pst, PropertyDescriptor pd, int parameterIndex) throws IllegalAccessException, InvocationTargetException, SQLException {
        Object invoke = pd.getReadMethod().invoke(entity);

        // If invoke is not an entity
        if (!BaseEntity.class.isAssignableFrom(pd.getPropertyType())) {
            pst.setObject(parameterIndex, pd.getReadMethod().invoke(entity));
            return;
        }

        // If invoke is an entity but is null
        if (invoke == null) {
            throw new DaoException("insert or update on table " + entity.getClass().getSimpleName()
                    + "violates foreign key constraint");
        }

        // if invoke not null cast to BaseEntity
        BaseEntity baseEntity =  (BaseEntity) invoke;
        Integer id = baseEntity.getId();

        if (id == null) {
            throw new DaoException("insert or update on table " + entity.getClass().getSimpleName()
                    + "violates foreign key constraint");
        }

        // if everything is fine
        pst.setInt(parameterIndex, id);
    }


}

package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.GenericDao;
import com.epam.aa.sportfinder.model.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.List;

public abstract class JdbcBaseDao<T extends BaseEntity> implements GenericDao<T> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcBaseDao.class);

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

    // todo if deleted do not return
    @Override
    public T findById(Integer id) throws DaoException{
        if (id == null)
            throw new DaoException("Could not find element by id, id is null", new NullPointerException());
        if (id < 1)
            throw new DaoException(new IllegalArgumentException("Could not find element by id, id cannot be less than 1"));

        Query query = Query.getQuery(entityClass, Query.Type.FIND_BY_ID);
        try (PreparedStatement pst = getConnection().prepareStatement(query.getQueryString())){
            pst.setObject(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return map(rs, query.getDaoBean());
                else return null;
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find element by id " + id, e);
        }
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
        T entity;
        try {
            entity = entityClass.newInstance();
            for (PropertyDescriptor pd : daoBean.getPropertyDescriptors()) {
                mapProperty(resultSet, entity, pd);
            }
        } catch (InstantiationException | IllegalAccessException | SQLException | InvocationTargetException e) {
            throw new DaoException("Could not assign properties from db to object", e);
        }
        return entity;
    }

    private void mapProperty(ResultSet resultSet, T entity, PropertyDescriptor pd) throws IllegalAccessException, InvocationTargetException, SQLException, InstantiationException {
        // If property is not an entity
        Class<?> propertyType = pd.getPropertyType();
        if (!BaseEntity.class.isAssignableFrom(propertyType)) {
            pd.getWriteMethod().invoke(entity, resultSet.getObject(pd.getName()));
            return;
        }

        BaseEntity property = (BaseEntity) propertyType.newInstance();

        Object id = resultSet.getObject(pd.getName());
        // If id of entity is null
//        if (id == null) {
//            throw new DaoException("FindById Failed due to " + pd.getName() + "'s Id being null in a database");
//        }


        property.setId((Integer) id);
        pd.getWriteMethod().invoke(entity, property);
    }

    protected void fillPreparedStatement(T entity, PreparedStatement pst, PropertyDescriptor pd, int parameterIndex) throws IllegalAccessException, InvocationTargetException, SQLException {
        // If property is not an entity
        if (!BaseEntity.class.isAssignableFrom(pd.getPropertyType())) {
            pst.setObject(parameterIndex, pd.getReadMethod().invoke(entity));
            return;
        }

        Object invoke = pd.getReadMethod().invoke(entity);

        // If invoke is an entity but is null
        if (invoke == null) {
            pst.setObject(parameterIndex, null);
            logger.warn("insert or update on table " + entity.getClass().getSimpleName()
                    + "violates foreign key constraint");
            return;
        }

        // if invoke not null cast to BaseEntity
        BaseEntity baseEntity =  (BaseEntity) invoke;
        Integer id = baseEntity.getId();

        if (id == null) {
            pst.setObject(parameterIndex, null);
            logger.warn("insert or update on table " + entity.getClass().getSimpleName()
                    + "violates foreign key constraint");
            return;
        }

        // if everything is fine
        pst.setInt(parameterIndex, id);
    }


}

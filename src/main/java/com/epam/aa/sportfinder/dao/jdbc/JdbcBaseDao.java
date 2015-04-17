package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.GenericDao;
import com.epam.aa.sportfinder.model.BaseEntity;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.List;

public abstract class JdbcBaseDao<T extends BaseEntity> implements GenericDao<T> {
    private final Connection connection;

    public JdbcBaseDao(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public T insert(T entity) {
        if (entity.getId() != null) throw new DaoException("Insertion failed, id is not null");

//        QueryConstructor queryConstructor = QueryConstructor.createQueryConstructor(entity);
        Query query = Query.getQuery(entity.getClass(), Query.Type.INSERT);
        try (PreparedStatement pst = getConnection().prepareStatement(query.getQueryString(),
                Statement.RETURN_GENERATED_KEYS)){


            List<PropertyDescriptor> pds = query.getDaoBean().getPropertyDescriptorsWithoutId();


            for (PropertyDescriptor pd : pds) {
                pst.setObject(pds.indexOf(pd) + 1, pd.getReadMethod().invoke(entity));
            }

            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) entity.setId(rs.getInt(1));
                else throw new DaoException("Id was not generated");
            }
        } catch (SQLException | IllegalAccessException | InvocationTargetException e) {
            throw new DaoException("Updating failed", e);
        }
        return entity;
    }

    @Override
    public void update(T entity) {
        if (entity.getId() == null) {
            throw new DaoException("Update failed, id is null", new NullPointerException());
        }

//        QueryConstructor queryConstructor = QueryConstructor.createQueryConstructor(entity);
        Query query = Query.getQuery(entity.getClass(), Query.Type.UPDATE);
        try (PreparedStatement pst = getConnection().prepareStatement(query.getQueryString())){

            List<PropertyDescriptor> pds = query.getDaoBean().getPropertyDescriptorsWithoutId();

            int count = 1;
            for (PropertyDescriptor pd : pds) {
                pst.setObject(pds.indexOf(pd) + 1, pd.getReadMethod().invoke(entity));
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
    public T delete(T entity) {
        if (entity.getId() == null) {
            throw new DaoException("Deletion failed, id is null", new NullPointerException());
        }
//        String deleteQuery = QueryConstructor.getDeleteQuery(entity);
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
}

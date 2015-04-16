package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.GenericDao;
import com.epam.aa.sportfinder.model.BaseEntity;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.List;

public abstract class JdbcBaseDao<T extends BaseEntity> implements GenericDao<T> {
    private Connection connection;

    public JdbcBaseDao(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void insert(T entity) {
        if (entity.getId() != null) throw new DaoException("Insertion failed, entity's id is not null");
        Query query = Query.getQuery(entity, Query.INSERT);
        try (PreparedStatement pst = getConnection().prepareStatement(
                query.getSqlQuery(),
                Statement.RETURN_GENERATED_KEYS)){


            List<PropertyDescriptor> pds = query.getPropertyDescriptors();

            for (PropertyDescriptor pd : pds) {
                pst.setObject(pds.indexOf(pd) + 1, pd.getReadMethod().invoke(entity));
            }

            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) entity.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new DaoException("Insertion failed", e);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new DaoException("Failed to iterate over property descriptors", e);
        }
    }
}

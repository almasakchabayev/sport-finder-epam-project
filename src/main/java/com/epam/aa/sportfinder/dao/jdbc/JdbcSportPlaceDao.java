package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.SportPlaceDao;
import com.epam.aa.sportfinder.model.Address;
import com.epam.aa.sportfinder.model.FloorCoverage;
import com.epam.aa.sportfinder.model.Sport;
import com.epam.aa.sportfinder.model.SportPlace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcSportPlaceDao extends JdbcBaseDao<SportPlace> implements SportPlaceDao {
    //TODO: delete constructors
    public JdbcSportPlaceDao(Connection connection) {
        super(connection);
    }

    // TODO: Need a transaction fot that
    public SportPlace insertCorrespondingSports(SportPlace sportPlace) {
        if (sportPlace.getSports() == null)
            throw new DaoException("List of Sports is null, cannot be inserted");

        String sql = getSqlForJoinTable(sportPlace);

        try (Statement st = getConnection().createStatement()){
            st.execute(sql);
        } catch (SQLException e) {
            throw new DaoException("Insertion failed", e);
        }
        return sportPlace;
    }

    private String getSqlForJoinTable(SportPlace sportPlace) {
        StringBuffer insertSportPlaceWithSportsBuffer = new StringBuffer("INSERT INTO SportPlace_Sport " +
                "(sport_id, sportPlace_id) VALUES ");

        Integer sportPlaceId = sportPlace.getId();
        String prefix = "";
        for (Sport sport : sportPlace.getSports()) {
            insertSportPlaceWithSportsBuffer.append(prefix);
            prefix = ", ";
            insertSportPlaceWithSportsBuffer.append("(");
            insertSportPlaceWithSportsBuffer.append(sport.getId());
            insertSportPlaceWithSportsBuffer.append(", ");
            insertSportPlaceWithSportsBuffer.append(sportPlaceId);
            insertSportPlaceWithSportsBuffer.append(")");
        }

        return insertSportPlaceWithSportsBuffer.append(";").toString();
    }
}

package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.SportPlaceDao;
import com.epam.aa.sportfinder.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcSportPlaceDao extends JdbcBaseDao<SportPlace> implements SportPlaceDao {
    private static final Logger logger = LoggerFactory.getLogger(JdbcSportPlaceDao.class);

    public JdbcSportPlaceDao(Connection connection) {
        super(connection);
    }

    public SportPlace insertCorrespondingSports(SportPlace sportPlace) throws DaoException {
        if (sportPlace.getSports() == null)
            throw new DaoException("List of Sports is null, cannot be inserted");

        String sql = getSqlForSportPlaceSportTable(sportPlace);

        try (Statement st = getConnection().createStatement()){
            st.execute(sql);
        } catch (SQLException e) {
            throw new DaoException("Insertion failed", e);
        }
        return sportPlace;
    }

    @Override
    public SportPlace removeSportFromCorrespondingSports(SportPlace sportPlace, Sport sport) throws DaoException {
        if(sportPlace.getId() == null || sport.getId() == null)
            throw new DaoException("Could not remove sport, id is null");

        if (sportPlace.getSports() == null)
            throw new DaoException("List of Sports is null, cannot be removed");

        String sql = "DELETE FROM SportPlace_Sport WHERE sportPlace_id = " + sportPlace.getId() +
                " AND sport_id = " + sport.getId() + ";";
        try (Statement st = getConnection().createStatement()){
            st.execute(sql);
        } catch (SQLException e) {
            throw new DaoException("Removal failed", e);
        }
        if (sportPlace.containsSport(sport))
            sportPlace.removeSport(sport);
        return sportPlace;
    }

    @Override
    public SportPlace addSportToCorrespondingSports(SportPlace sportPlace, Sport sport) throws DaoException {
        if(sportPlace.getId() == null || sport.getId() == null)
            throw new DaoException("Could not add sport, id is null");

        String sql = "INSERT INTO SportPlace_Sport " +
                "(sport_id, sportPlace_id) VALUES (" +
                sport.getId() + ", " +sportPlace.getId() +");";

        try (Statement st = getConnection().createStatement()){
            st.execute(sql);
        } catch (SQLException e) {
            throw new DaoException("Insertion failed", e);
        }

        if (sportPlace.getSports() == null)
            sportPlace.setSports(new ArrayList<>());

        if (!sportPlace.containsSport(sport))
            sportPlace.addSport(sport);
        return sportPlace;
    }

    @Override
    public List<Integer> findCorrespondingSportIds(SportPlace sportPlace) throws DaoException {
        Integer sportPlaceId = sportPlace.getId();
        if (sportPlaceId == null)
            throw new DaoException("Could not find sports, id is null");

        String sql = "SELECT sport_id, sportPlace_id FROM SportPlace_Sport " +
                "WHERE sportPlace_id = " + sportPlaceId;

        List<Integer> sportIds = new ArrayList<>();
        try (Statement st = getConnection().createStatement()) {
            try (ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    sportIds.add(rs.getInt("sport_id"));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot find corresponding sports", e);
        }
        return sportIds;
    }

    @Override
    public List<SportPlace> findByManager(Manager manager) {
        Integer managerId = manager.getId();
        if (managerId == null)
            throw new DaoException("Could not find sportPlaces, Manager id is null");

        String sql = "SELECT id, uuid, deleted, size, floorcoverage,  " +
                "capacity, indoor, changingRoom, shower, lightening, tribuneCapacity, " +
                "otherInfrastructureFeatures, pricePerHour, description, address, manager " +
                "FROM SportPlace WHERE deleted = FALSE AND manager = " + manager.getId();

        List<SportPlace> sportPlaces = new ArrayList<>();
        try (Statement st = getConnection().createStatement()) {
            try (ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    SportPlace sportPlace = getSportPlaceFromResultSet(rs);
                    sportPlaces.add(sportPlace);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot find SportPlaces given Manager with email  " + manager.getEmail(), e);
        }
        return sportPlaces;
    }

    @Override
    public void deleteCorrespondingSports(SportPlace sportPlace) {
        Integer id = sportPlace.getId();
        if(id == null)
            throw new DaoException("Could not remove sports, sportPlace id is null");

        String sql = "DELETE FROM SportPlace_Sport WHERE sportPlace_id = " + id;
        try (Statement st = getConnection().createStatement()){
            st.execute(sql);
            logger.info("deleted sport ids from sportPlace_sport table, where sportPlace_id = {}", id);
        } catch (SQLException e) {
            throw new DaoException("Removal failed", e);
        }
        // not removing
    }

    @Override
    public SportPlace insertCorrespondingImages(SportPlace sportPlace) throws DaoException {
        if (sportPlace.getImages() == null)
            throw new DaoException("List of Images is null, cannot be inserted");

        String sql = getSqlForSportPlaceImageTable(sportPlace);

        try (Statement st = getConnection().createStatement()){
            st.execute(sql);
        } catch (SQLException e) {
            throw new DaoException("Insertion failed", e);
        }
        return sportPlace;
    }

    @Override
    public List<Integer> findCorrespondingImageIds(SportPlace sportPlace) throws DaoException {
        Integer sportPlaceId = sportPlace.getId();
        if (sportPlaceId == null)
            throw new DaoException("Could not find images, id is null");

        String sql = "SELECT image_id, sportPlace_id FROM SportPlace_Image " +
                "WHERE sportPlace_id = " + sportPlaceId;

        List<Integer> imageIds = new ArrayList<>();
        try (Statement st = getConnection().createStatement()) {
            try (ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    imageIds.add(rs.getInt("image_id"));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot find corresponding images", e);
        }
        return imageIds;
    }

    @Override
    public void deleteCorrespondingImages(SportPlace sportPlace) {
        Integer id = sportPlace.getId();
        if(id == null)
            throw new DaoException("Could not remove images, sportPlace id is null");

        String sql = "DELETE FROM SportPlace_Image WHERE sportPlace_id = " + id;
        try (Statement st = getConnection().createStatement()){
            st.execute(sql);
            logger.info("deleted image ids from sportPlace_Image table, where sportPlace_id = {}", id);
        } catch (SQLException e) {
            throw new DaoException("Removal failed", e);
        }
        // not removing
    }

    @Override
    public List<SportPlace> findDeletedByManager(Manager manager) {
        Integer managerId = manager.getId();
        if (managerId == null)
            throw new DaoException("Could not find sportPlaces, Manager id is null");

        String sql = "SELECT id, uuid, deleted, size, floorcoverage,  " +
                "capacity, indoor, changingRoom, shower, lightening, tribuneCapacity, " +
                "otherInfrastructureFeatures, pricePerHour, description, address, manager " +
                "FROM SportPlace WHERE deleted = TRUE AND manager = " + manager.getId();

        List<SportPlace> sportPlaces = new ArrayList<>();
        try (Statement st = getConnection().createStatement()) {
            try (ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    SportPlace sportPlace = getSportPlaceFromResultSet(rs);
                    sportPlaces.add(sportPlace);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot find SportPlaces given Manager with email  " + manager.getEmail(), e);
        }
        return sportPlaces;
    }

    @Override
    public void undelete(SportPlace sportPlace) {
        Integer id = sportPlace.getId();
        if(id == null)
            throw new DaoException("Could not undelete sport place, sportPlace id is null");

        String sql = "UPDATE SportPlace SET deleted = FALSE WHERE id = " + id;
        try (Statement st = getConnection().createStatement()){
            st.execute(sql);
            logger.info("performed undeleted on sportPlace, where sportPlace_id = {}", id);
        } catch (SQLException e) {
            throw new DaoException("Undelete failed", e);
        }
        // not removing
    }

    @Override
    public SportPlace findDeletedOrNonDeletedById(Integer id) {
        if (id == null)
            throw new DaoException("Could not find sportPlaces, Manager id is null");

        String sql = "SELECT id, uuid, deleted, size, floorcoverage,  " +
                "capacity, indoor, changingRoom, shower, lightening, tribuneCapacity, " +
                "otherInfrastructureFeatures, pricePerHour, description, address, manager " +
                "FROM SportPlace WHERE id = " + id;

        try (Statement st = getConnection().createStatement()) {
            try (ResultSet rs = st.executeQuery(sql)) {
                if (rs.next()) {
                    return getSportPlaceFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot find SportPlace given id " + id, e);
        }
        return null;
    }


    private String getSqlForSportPlaceSportTable(SportPlace sportPlace) {
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

    private String getSqlForSportPlaceImageTable(SportPlace sportPlace) {
        StringBuffer insertSportPlaceWithSportsBuffer = new StringBuffer("INSERT INTO SportPlace_Image " +
                "(image_id, sportPlace_id) VALUES ");

        Integer sportPlaceId = sportPlace.getId();
        String prefix = "";
        for (Image image : sportPlace.getImages()) {
            insertSportPlaceWithSportsBuffer.append(prefix);
            prefix = ", ";
            insertSportPlaceWithSportsBuffer.append("(");
            insertSportPlaceWithSportsBuffer.append(image.getId());
            insertSportPlaceWithSportsBuffer.append(", ");
            insertSportPlaceWithSportsBuffer.append(sportPlaceId);
            insertSportPlaceWithSportsBuffer.append(")");
        }

        return insertSportPlaceWithSportsBuffer.append(";").toString();
    }

    private SportPlace getSportPlaceFromResultSet(ResultSet resultSet) throws SQLException {
        SportPlace sportPlaceFromDatabase = new SportPlace();
        sportPlaceFromDatabase.setId(resultSet.getInt("id"));
        sportPlaceFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
        sportPlaceFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
        sportPlaceFromDatabase.setIndoor(resultSet.getBoolean("indoor"));
        sportPlaceFromDatabase.setDescription(resultSet.getString("description"));
        sportPlaceFromDatabase.setChangingRoom(resultSet.getBoolean("changingRoom"));
        sportPlaceFromDatabase.setCapacity(resultSet.getInt("capacity"));

        Address addressFromDatabase = new Address();
        Integer addressId = (Integer) resultSet.getObject("address");
        addressFromDatabase.setId(addressId);
        sportPlaceFromDatabase.setAddress(addressFromDatabase);

        FloorCoverage floorCoverageFromDatabase = new FloorCoverage();
        Integer floorCoverageId = (Integer) resultSet.getObject("floorCoverage");
        floorCoverageFromDatabase.setId(floorCoverageId);
        sportPlaceFromDatabase.setFloorCoverage(floorCoverageFromDatabase);

        Manager managerFromDatabase = new Manager();
        Integer managerId = (Integer) resultSet.getObject("manager");
        managerFromDatabase.setId(managerId);
        sportPlaceFromDatabase.setManager(managerFromDatabase);

        sportPlaceFromDatabase.setLightening(resultSet.getBoolean("lightening"));
        sportPlaceFromDatabase.setOtherInfrastructureFeatures(resultSet.getString("otherInfrastructureFeatures"));
        sportPlaceFromDatabase.setPricePerHour((BigDecimal) resultSet.getObject("pricePerHour"));
        sportPlaceFromDatabase.setSize(resultSet.getString("size"));
        sportPlaceFromDatabase.setShower(resultSet.getBoolean("shower"));
        sportPlaceFromDatabase.setTribuneCapacity(resultSet.getInt("tribuneCapacity"));

        return sportPlaceFromDatabase;
    }
}

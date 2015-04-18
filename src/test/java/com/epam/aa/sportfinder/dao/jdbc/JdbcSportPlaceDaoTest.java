package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.DaoManager;
import com.epam.aa.sportfinder.dao.SportPlaceDao;
import com.epam.aa.sportfinder.model.Address;
import com.epam.aa.sportfinder.model.FloorCoverage;
import com.epam.aa.sportfinder.model.Sport;
import com.epam.aa.sportfinder.model.SportPlace;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class JdbcSportPlaceDaoTest extends TestConfig {


    @Test(expected = DaoException.class)
    public void testInsertFailsIfIdNotNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        SportPlaceDao dao = daoManager.getDao(SportPlace.class);

        SportPlace sportPlace = new SportPlace();
        sportPlace.setId(1);

        dao.insert(sportPlace);
    }

    @Test(expected = DaoException.class)
    public void testInsertFailsIfUuidAlreadyExists() throws Exception {
        DaoManager daoManager = getDaoManager();
        SportPlaceDao dao = daoManager.getDao(SportPlace.class);

        SportPlace sportPlace = new SportPlace();
        dao.insert(sportPlace);

        SportPlace sportPlaceWithSameUuid = new SportPlace();
        sportPlaceWithSameUuid.setUuid(sportPlace.getUuid());

        dao.insert(sportPlaceWithSameUuid);
    }

    @Test
    public void testInsertSuccessWithoutIdAndUuidAndWith() throws Exception {
        DaoManager daoManager = getDaoManager();
        SportPlaceDao dao = daoManager.getDao(SportPlace.class);

        Connection connection = getDataSource().getConnection();

        SportPlace sportPlace = new SportPlace();
        sportPlace.setIndoor(true);
        sportPlace.setDescription("Traditional SportPlace");
        sportPlace.setChangingRoom(true);
        sportPlace.setCapacity(40);
//        sportPlace.setAddress(new Address());
//        sportPlace.setFloorCoverage(new FloorCoverage());
        sportPlace.setLightening(false);
        sportPlace.setOtherInfrastructureFeatures("there are balls for rent");
        sportPlace.setPricePerHour(BigDecimal.valueOf(9000.00));
        sportPlace.setSize("60x60");
        sportPlace.setShower(false);
//        sportPlace.setSports(new ArrayList<Sport>());
        sportPlace.setTribuneCapacity(5000);

        dao.insert(sportPlace);

        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT id, uuid, deleted, size, floorcoverage,  " +
                "capacity, indoor, changingRoom, shower, lightening, tribuneCapacity, " +
                "otherInfrastructureFeatures, pricePerHour, description, address " +
                "FROM SportPlace ORDER BY id DESC LIMIT 1");
        SportPlace sportPlaceFromDatabase = new SportPlace();

        if (resultSet.next()) {
            sportPlaceFromDatabase.setId(resultSet.getInt("id"));
            sportPlaceFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
            sportPlaceFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
            sportPlaceFromDatabase.setIndoor(resultSet.getBoolean("indoor"));
            sportPlaceFromDatabase.setDescription(resultSet.getString("description"));
            sportPlaceFromDatabase.setChangingRoom(resultSet.getBoolean("changingRoom"));
            sportPlaceFromDatabase.setCapacity(resultSet.getInt("capacity"));
//            sportPlaceFromDatabase.setAddress((Address) resultSet.getObject("address"));
//            sportPlaceFromDatabase.setFloorCoverage((FloorCoverage) resultSet.getObject("floorCoverage"));
            sportPlaceFromDatabase.setLightening(resultSet.getBoolean("lightening"));
            sportPlaceFromDatabase.setOtherInfrastructureFeatures(resultSet.getString("otherInfrastructureFeatures"));
            sportPlaceFromDatabase.setPricePerHour((BigDecimal) resultSet.getObject("pricePerHour"));
            sportPlaceFromDatabase.setSize(resultSet.getString("size"));
            sportPlaceFromDatabase.setShower(resultSet.getBoolean("shower"));
//            sportPlaceFromDatabase.setSports(resultSet.getBoolean("shower"));
            sportPlaceFromDatabase.setTribuneCapacity(resultSet.getInt("tribuneCapacity"));

        }
        resultSet.close();
        st.close();
        connection.close();

        assertSportPlacesEqualFromDaoPerspective(sportPlace, sportPlaceFromDatabase);
    }

//    @Test(expected = DaoException.class)
//    public void testUpdateFailsIfIdIsNull() throws Exception {
//        DaoManager daoManager = getDaoManager();
//        SportPlaceDao dao = daoManager.getDao(SportPlace.class);
//
//        SportPlace sportPlace = new SportPlace();
//        sportPlace.setName("parket");
//
//        dao.update(sportPlace);
//    }
//
//    @Test
//    public void testUpdateSuccessIfIdNotNull() throws Exception {
//        DaoManager daoManager = getDaoManager();
//        SportPlaceDao dao = daoManager.getDao(SportPlace.class);
//
//        Connection connection = getDataSource().getConnection();
//
//        SportPlace sportPlace = new SportPlace();
//        sportPlace.setId(2);
//        sportPlace.setName("lenoleum");
//
//        dao.update(sportPlace);
//
//        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, name " +
//                "FROM SportPlace WHERE id = ?");
//        pst.setInt(1, sportPlace.getId());
//        ResultSet resultSet = pst.executeQuery();
//        SportPlace sportPlaceFromDatabase = new SportPlace();
//
//        if (resultSet.next()) {
//            sportPlaceFromDatabase.setId(resultSet.getInt("id"));
//            sportPlaceFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
//            sportPlaceFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
//            sportPlaceFromDatabase.setName(resultSet.getString("name"));
//        }
//        resultSet.close();
//        pst.close();
//        connection.close();
//
//        assertSportPlacesEqualFromDaoPerspective(sportPlace, sportPlaceFromDatabase);
//    }
//
//    @Test
//    public void testDeleteInDbAndAssignTrueToObject() throws Exception {
//        DaoManager daoManager = getDaoManager();
//        SportPlaceDao dao = daoManager.getDao(SportPlace.class);
//
//        Connection connection = getDataSource().getConnection();
//        SportPlace sportPlace = new SportPlace();
//        sportPlace.setId(1);
//        dao.delete(sportPlace);
//
//        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, name " +
//                "FROM SportPlace WHERE id = ?");
//        pst.setInt(1, sportPlace.getId());
//        ResultSet resultSet = pst.executeQuery();
//        SportPlace sportPlaceFromDatabase = new SportPlace();
//
//        if (resultSet.next()) {
//            sportPlaceFromDatabase.setId(resultSet.getInt("id"));
//            sportPlaceFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
//            sportPlaceFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
//            sportPlaceFromDatabase.setName(resultSet.getString("name"));
//        }
//        resultSet.close();
//        pst.close();
//        connection.close();
//
//        assertEquals(sportPlaceFromDatabase.isDeleted(), true);
//        assertEquals(sportPlace.isDeleted(), sportPlaceFromDatabase.isDeleted());
//    }
//
//    @Test(expected = DaoException.class)
//    public void testFindByIdFailsIfIdIsNull() throws Exception {
//        DaoManager daoManager = getDaoManager();
//        SportPlaceDao dao = daoManager.getDao(SportPlace.class);
//
//        SportPlace dummysportPlace = new SportPlace();
//        SportPlace sportPlace = dao.findById(dummysportPlace.getId());
//    }
//
//    @Test(expected = DaoException.class)
//    public void testFindByIdFailsIfIdIsNegative() throws Exception {
//        DaoManager daoManager = getDaoManager();
//        SportPlaceDao dao = daoManager.getDao(SportPlace.class);
//
//        SportPlace sportPlace = dao.findById(-1);
//    }
//
//    @Test(expected = DaoException.class)
//    public void testFindByIdFailsIfElementCouldNotBeFounded() throws Exception {
//        DaoManager daoManager = getDaoManager();
//        SportPlaceDao dao = daoManager.getDao(SportPlace.class);
//
//        SportPlace sportPlace = dao.findById(100000000);
//    }
//
//    @Test
//    public void testFindByIdSuccessIfValidId() throws Exception {
//        DaoManager daoManager = getDaoManager();
//        SportPlaceDao dao = daoManager.getDao(SportPlace.class);
//
//        Connection connection = getDataSource().getConnection();
//        SportPlace sportPlace = dao.findById(1);
//
//        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, name " +
//                "FROM SportPlace WHERE id = ?");
//        pst.setInt(1, sportPlace.getId());
//        ResultSet resultSet = pst.executeQuery();
//        SportPlace sportPlaceFromDatabase = new SportPlace();
//
//        if (resultSet.next()) {
//            sportPlaceFromDatabase.setId(resultSet.getInt("id"));
//            sportPlaceFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
//            sportPlaceFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
//            sportPlaceFromDatabase.setName(resultSet.getString("name"));}
//
//        connection.close();
//
//        assertSportPlacesEqualFromDaoPerspective(sportPlace, sportPlaceFromDatabase);
//    }

    public void assertSportPlacesEqualFromDaoPerspective(SportPlace sportPlace, SportPlace sportPlaceFromDatabase) {
        assertEquals(sportPlace.getId(), sportPlaceFromDatabase.getId());
        assertEquals(sportPlace.getUuid(), sportPlaceFromDatabase.getUuid());
        assertEquals(sportPlace.isDeleted(), sportPlaceFromDatabase.isDeleted());
        assertEquals(sportPlace.isIndoor(), sportPlaceFromDatabase.isIndoor());
        assertEquals(sportPlace.getDescription(), sportPlaceFromDatabase.getDescription());
        assertEquals(sportPlace.isChangingRoom(), sportPlaceFromDatabase.isChangingRoom());
        assertEquals(sportPlace.getCapacity(), sportPlaceFromDatabase.getCapacity());
        assertEquals(sportPlace.getAddress(), sportPlaceFromDatabase.getAddress());
        assertEquals(sportPlace.getFloorCoverage(), sportPlaceFromDatabase.getFloorCoverage());
        assertEquals(sportPlace.isLightening(), sportPlaceFromDatabase.isLightening());
        assertEquals(sportPlace.getOtherInfrastructureFeatures(), sportPlaceFromDatabase.getOtherInfrastructureFeatures());
        assertEquals(sportPlace.getPricePerHour().compareTo(sportPlaceFromDatabase.getPricePerHour()), 0);
        assertEquals(sportPlace.getSize(), sportPlaceFromDatabase.getSize());
        assertEquals(sportPlace.isShower(), sportPlaceFromDatabase.isShower());
        assertEquals(sportPlace.getSports(), sportPlaceFromDatabase.getSports());
        assertEquals(sportPlace.getTribuneCapacity(), sportPlaceFromDatabase.getTribuneCapacity());

    }
}
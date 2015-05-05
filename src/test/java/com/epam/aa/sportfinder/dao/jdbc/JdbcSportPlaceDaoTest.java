package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.DaoManager;
import com.epam.aa.sportfinder.dao.SportPlaceDao;
import com.epam.aa.sportfinder.model.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class JdbcSportPlaceDaoTest extends TestConfig {


    @Test(expected = DaoException.class)
    public void testInsertFailsIfIdNotNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);

            SportPlace sportPlace = new SportPlace();
            sportPlace.setId(1);

            return dao.insert(sportPlace);
        });
    }

    @Test(expected = DaoException.class)
    public void testInsertFailsIfUuidAlreadyExists() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);

            SportPlace sportPlace = new SportPlace();
            dao.insert(sportPlace);

            SportPlace sportPlaceWithSameUuid = new SportPlace();
            sportPlaceWithSameUuid.setUuid(sportPlace.getUuid());

            return dao.insert(sportPlaceWithSameUuid);
        });

    }

    @Test(expected = DaoException.class)
    public void testInsertFailsIfAddressIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);

            SportPlace sportPlace = new SportPlace();
            FloorCoverage floorCoverage = new FloorCoverage();
            floorCoverage.setId(1);
            sportPlace.setFloorCoverage(floorCoverage);

            return dao.insert(sportPlace);
        });
    }

    @Test(expected = DaoException.class)
    public void testInsertFailsIfFloorCoverageIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);

            SportPlace sportPlace = new SportPlace();
            Address address = new Address();
            address.setId(1);
            sportPlace.setAddress(address);

            return dao.insert(sportPlace);
        });
    }

    @Test(expected = DaoException.class)
    public void testInsertFailsIfAddressIdIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);

            SportPlace sportPlace = new SportPlace();
            FloorCoverage floorCoverage = new FloorCoverage();
            floorCoverage.setId(1);
            sportPlace.setFloorCoverage(floorCoverage);
            sportPlace.setAddress(new Address());

            return dao.insert(sportPlace);
        });
    }

    @Test(expected = DaoException.class)
    public void testInsertFailsIfFloorCoverageIdIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);

            SportPlace sportPlace = new SportPlace();
            Address address = new Address();
            address.setId(1);
            sportPlace.setAddress(address);
            sportPlace.setFloorCoverage(new FloorCoverage());

            return dao.insert(sportPlace);
        });
    }

    @Test
    public void testInsertSuccessWithoutSports() throws Exception {
        DaoManager daoManager = getDaoManager();
        SportPlace sportPlace =  new SportPlace();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);
            initSportPlaceWithoutSports(sportPlace);
            return dao.insert(sportPlace);
        });

        Connection connection = getDataSource().getConnection();
        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT id, uuid, deleted, size, floorcoverage,  " +
                "capacity, indoor, changingRoom, shower, lightening, tribuneCapacity, " +
                "otherInfrastructureFeatures, pricePerHour, description, address, manager " +
                "FROM SportPlace ORDER BY id DESC LIMIT 1");

        SportPlace sportPlaceFromDatabase = null;
        if (resultSet.next()) {
            sportPlaceFromDatabase = getSportPlaceFromResultSet(resultSet);
        }
        resultSet.close();
        st.close();
        connection.close();

        assertSportPlacesEqual(sportPlace, sportPlaceFromDatabase);
    }

    @Test
    public void testInsertSportsSuccess() throws Exception {
        DaoManager daoManager = getDaoManager();
        SportPlace sportPlace = new SportPlace();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);

            initSportPlaceWithoutSports(sportPlace);
            sportPlace.setId(1);
            ArrayList<Sport> sportArrayList = new ArrayList<>();
            Sport sport1 = new Sport();
            sport1.setId(1);
            sportArrayList.add(sport1);
            Sport sport2 = new Sport();
            sport2.setId(2);
            sportArrayList.add(sport2);
            sportPlace.setSports(sportArrayList);

            return dao.insertCorrespondingSports(sportPlace);
        });

        Connection connection = getDataSource().getConnection();
        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT sport_id, sportPlace_id " +
                "FROM SportPlace_Sport WHERE sportPlace_id = " + sportPlace.getId());


        ArrayList<Integer> sportIdsFromDatabase = new ArrayList<>();
        while (resultSet.next()) {
            sportIdsFromDatabase.add(resultSet.getInt("sport_id"));
        }

        resultSet.close();
        st.close();
        connection.close();

        List<Sport> sports = sportPlace.getSports();
        assertEquals(sports.size(), sportIdsFromDatabase.size());
        for (int i = 0; i < sports.size(); i++) {
            assertEquals(sports.get(i).getId(), sportIdsFromDatabase.get(i));
        }
    }

    @Test(expected = DaoException.class)
    public void testUpdateFailsIfIdIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);

            SportPlace sportPlace = new SportPlace();

            dao.update(sportPlace);
            return null;
        });
    }

    @Test(expected = DaoException.class)
    public void testUpdateFailsIfAddressIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);

            SportPlace sportPlace = new SportPlace();
            sportPlace.setId(1);
            FloorCoverage floorCoverage = new FloorCoverage();
            floorCoverage.setId(1);
            sportPlace.setFloorCoverage(floorCoverage);

            dao.update(sportPlace);
            return null;
        });
    }

    @Test(expected = DaoException.class)
    public void testUpdateFailsIfFloorCoverageIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);

            SportPlace sportPlace = new SportPlace();
            sportPlace.setId(1);
            Address address = new Address();
            address.setId(1);
            sportPlace.setAddress(address);

            dao.update(sportPlace);
            return null;
        });
    }

    @Test(expected = DaoException.class)
    public void testUpdateFailsIfAddressIdIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);

            SportPlace sportPlace = new SportPlace();
            sportPlace.setId(1);
            FloorCoverage floorCoverage = new FloorCoverage();
            floorCoverage.setId(1);
            sportPlace.setFloorCoverage(floorCoverage);
            sportPlace.setAddress(new Address());

            dao.update(sportPlace);
            return null;
        });
    }

    @Test(expected = DaoException.class)
    public void testUpdateFailsIfFloorCoverageIdIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);

            SportPlace sportPlace = new SportPlace();
            sportPlace.setId(1);
            Address address = new Address();
            address.setId(1);
            sportPlace.setAddress(address);
            sportPlace.setFloorCoverage(new FloorCoverage());

            dao.update(sportPlace);
            return null;
        });
    }
    @Test
    public void testUpdateSuccessIfIdNotNullWithoutSports() throws Exception {
        DaoManager daoManager = getDaoManager();
        SportPlace sportPlace = new SportPlace();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);
            initSportPlaceWithoutSports(sportPlace);
            sportPlace.setId(2);
            dao.update(sportPlace);
            return null;
        });

        Connection connection = getDataSource().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, size, floorcoverage, " +
                "capacity, indoor, changingRoom, shower, lightening, tribuneCapacity, " +
                "otherInfrastructureFeatures, pricePerHour, description, address, manager " +
                "FROM SportPlace WHERE id = ?");
        pst.setInt(1, sportPlace.getId());
        ResultSet resultSet = pst.executeQuery();

        SportPlace sportPlaceFromDatabase = null;
        if (resultSet.next()) {
            sportPlaceFromDatabase = getSportPlaceFromResultSet(resultSet);
        }
        resultSet.close();
        pst.close();
        connection.close();

        assertSportPlacesEqual(sportPlace, sportPlaceFromDatabase);
    }

    @Test(expected = DaoException.class)
    public void testAddSportFailsIfSportIdIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);

            SportPlace sportPlace = new SportPlace();
            initSportPlaceWithoutSports(sportPlace);
            sportPlace.setId(2);

            return dao.addSportToCorrespondingSports(sportPlace, new Sport());
        });
    }

    @Test(expected = DaoException.class)
    public void testAddSportFailsIfSportPlaceIdIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);
            Sport sport = new Sport();
            sport.setId(1);
            return dao.addSportToCorrespondingSports(new SportPlace(), sport);
        });
    }

    @Test
    public void testAddSportSuccess() throws Exception {
        DaoManager daoManager = getDaoManager();
        SportPlace sportPlace = new SportPlace();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);

            initSportPlaceWithoutSports(sportPlace);
            sportPlace.setId(2);
            Sport sport = new Sport();
            sport.setId(1);

            return dao.addSportToCorrespondingSports(sportPlace, sport);
        });

        Connection connection = getDataSource().getConnection();
        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT sport_id, sportPlace_id " +
                "FROM SportPlace_Sport WHERE sportPlace_id = " + sportPlace.getId());


        ArrayList<Integer> sportIdsFromDatabase = new ArrayList<>();
        while (resultSet.next()) {
            sportIdsFromDatabase.add(resultSet.getInt("sport_id"));
        }
        resultSet.close();
        st.close();
        connection.close();

        assertEquals(true, sportIdsFromDatabase.contains(1));
    }

    @Test(expected = DaoException.class)
    public void testRemoveSportFailsIfSportIdIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);

            SportPlace sportPlace = new SportPlace();
            initSportPlaceWithoutSports(sportPlace);
            sportPlace.setId(2);

            return dao.removeSportFromCorrespondingSports(sportPlace, new Sport());
        });
    }

    @Test(expected = DaoException.class)
    public void testRemoveSportFailsIfSportPlaceIdIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);

            Sport sport = new Sport();
            sport.setId(1);
            return dao.removeSportFromCorrespondingSports(new SportPlace(), sport);
        });
    }

    @Test
    public void testRemoveSportSuccess() throws Exception {
        DaoManager daoManager = getDaoManager();
        SportPlace sportPlace = new SportPlace();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);
            initSportPlaceWithoutSports(sportPlace);
            sportPlace.setId(2);
            Sport sport = new Sport();
            sport.setId(2);
            return dao.removeSportFromCorrespondingSports(sportPlace, sport);
        });

        Connection connection = getDataSource().getConnection();
        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT sport_id, sportPlace_id " +
                "FROM SportPlace_Sport WHERE sportPlace_id = " + sportPlace.getId());


        ArrayList<Integer> sportIdsFromDatabase = new ArrayList<>();
        while (resultSet.next()) {
            sportIdsFromDatabase.add(resultSet.getInt("sport_id"));
        }

        resultSet.close();
        st.close();
        connection.close();

        assertEquals(false, sportIdsFromDatabase.contains(2));
        List<Sport> sports = sportPlace.getSports();
        assertEquals(sports.size(), sportIdsFromDatabase.size());
        for (int i = 0; i < sports.size(); i++) {
            assertEquals(sports.get(i).getId(), sportIdsFromDatabase.get(i));
        }    }


    @Test
    public void testDeleteInDbAndAssignTrueToObject() throws Exception {
        DaoManager daoManager = getDaoManager();
        SportPlace sportPlace = new SportPlace();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);
            initSportPlaceWithoutSports(sportPlace);
            sportPlace.setId(1);
            return dao.delete(sportPlace);
        });

        Connection connection = getDataSource().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, size, floorcoverage, " +
                "capacity, indoor, changingRoom, shower, lightening, tribuneCapacity, " +
                "otherInfrastructureFeatures, pricePerHour, description, address, manager " +
                "FROM SportPlace WHERE id = ?");
        pst.setInt(1, sportPlace.getId());
        ResultSet resultSet = pst.executeQuery();
        SportPlace sportPlaceFromDatabase = null;

        if (resultSet.next()) {
            sportPlaceFromDatabase = getSportPlaceFromResultSet(resultSet);
        }
        resultSet.close();
        pst.close();
        connection.close();

        assertEquals(sportPlaceFromDatabase.isDeleted(), true);
        assertEquals(sportPlace.isDeleted(), sportPlaceFromDatabase.isDeleted());
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfIdIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);

            SportPlace dummysportPlace = new SportPlace();
            return dao.findById(dummysportPlace.getId());
        });
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfIdIsNegative() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);
            return dao.findById(-1);
        });
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfElementCouldNotBeFounded() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);
            return dao.findById(100000000);
        });
    }

    @Test
    public void testFindByIdSuccessIfValidId() throws Exception {
        DaoManager daoManager = getDaoManager();
        SportPlace sportPlace = daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);
            return dao.findById(1);
        });

        Connection connection = getDataSource().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, size, floorcoverage, " +
                "capacity, indoor, changingRoom, shower, lightening, tribuneCapacity, " +
                "otherInfrastructureFeatures, pricePerHour, description, address, manager " +
                "FROM SportPlace WHERE id = ?");
        pst.setInt(1, sportPlace.getId());
        ResultSet resultSet = pst.executeQuery();
        SportPlace sportPlaceFromDatabase = null;

        if (resultSet.next())
            sportPlaceFromDatabase = getSportPlaceFromResultSet(resultSet);

        resultSet.close();
        pst.close();
        connection.close();

        assertSportPlacesEqual(sportPlace, sportPlaceFromDatabase);
    }

    @Test(expected = DaoException.class)
    public void testFindCorrespondingSportIdsFailsIfIdIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);

            SportPlace sportPlace = new SportPlace();
            return dao.findCorrespondingSportIds(sportPlace);
        });
    }

    @Test
    public void testFindCorrespondingSportIds() throws Exception {
        DaoManager daoManager = getDaoManager();
        SportPlace sportPlace = new SportPlace();
        List<Integer> correspondingSportIds = daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);
            sportPlace.setId(2);
            return dao.findCorrespondingSportIds(sportPlace);
        });

        Connection connection = getDataSource().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT sport_id, sportPlace_id " +
                "FROM SportPlace_Sport WHERE sportPlace_id = ?");
        pst.setInt(1, sportPlace.getId());
        ResultSet resultSet = pst.executeQuery();
        List<Integer> IdsFromDatabase = new ArrayList<>();

        while (resultSet.next())
            IdsFromDatabase.add(resultSet.getInt("sport_id"));

        resultSet.close();
        pst.close();
        connection.close();

        assertEquals(correspondingSportIds, IdsFromDatabase);
    }

    @Test
    public void testFindByManager() throws Exception {
        DaoManager daoManager = getDaoManager();
        Manager m = new Manager();
        List<SportPlace> sportPlaces = daoManager.executeTx(daoManager1 -> {
            SportPlaceDao dao = daoManager.getDao(SportPlace.class);
            m.setId(1);
            return dao.findByManager(m);
        });

        Connection connection = getDataSource().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, size, floorcoverage, " +
                "capacity, indoor, changingRoom, shower, lightening, tribuneCapacity, " +
                "otherInfrastructureFeatures, pricePerHour, description, address, manager " +
                "FROM SportPlace WHERE manager = ?");
        pst.setInt(1, m.getId());
        ResultSet resultSet = pst.executeQuery();
        List<SportPlace> sportPlacesFromDatabase = new ArrayList<>();

        while (resultSet.next()) {
            SportPlace sportPlace = getSportPlaceFromResultSet(resultSet);
            sportPlacesFromDatabase.add(sportPlace);
        }
        resultSet.close();
        pst.close();
        connection.close();

        assertEquals(sportPlaces.size(), sportPlacesFromDatabase.size());
        for (int i = 0; i < sportPlaces.size(); i++) {
            assertSportPlacesEqual(sportPlaces.get(i), sportPlacesFromDatabase.get(i));
        }
    }

    public void assertSportPlacesEqual(SportPlace sportPlace, SportPlace sportPlaceFromDatabase) {
        assertEquals(sportPlace.getId(), sportPlaceFromDatabase.getId());
        assertEquals(sportPlace.getUuid(), sportPlaceFromDatabase.getUuid());
        assertEquals(sportPlace.isDeleted(), sportPlaceFromDatabase.isDeleted());
        assertEquals(sportPlace.isIndoor(), sportPlaceFromDatabase.isIndoor());
        assertEquals(sportPlace.getDescription(), sportPlaceFromDatabase.getDescription());
        assertEquals(sportPlace.isChangingRoom(), sportPlaceFromDatabase.isChangingRoom());
        assertEquals(sportPlace.getCapacity(), sportPlaceFromDatabase.getCapacity());

        assertEquals(sportPlace.getAddress().getId(), sportPlaceFromDatabase.getAddress().getId());
        assertEquals(sportPlace.getFloorCoverage().getId(), sportPlaceFromDatabase.getFloorCoverage().getId());

        assertEquals(sportPlace.isLightening(), sportPlaceFromDatabase.isLightening());
        assertEquals(sportPlace.getOtherInfrastructureFeatures(), sportPlaceFromDatabase.getOtherInfrastructureFeatures());
        assertEquals(sportPlace.getPricePerHour().compareTo(sportPlaceFromDatabase.getPricePerHour()), 0);
        assertEquals(sportPlace.getSize(), sportPlaceFromDatabase.getSize());
        assertEquals(sportPlace.isShower(), sportPlaceFromDatabase.isShower());

        List<Sport> sportsFromDataBase = sportPlaceFromDatabase.getSports();
        List<Sport> sports = sportPlace.getSports();
        if (sports != null && sportsFromDataBase != null) {
            assertEquals(sports.size(), sportsFromDataBase.size());
            for (int i = 0; i < sports.size(); i++) {
                assertEquals(sports.get(i).getId(), sportsFromDataBase.get(i).getId());
            }
        }
        assertEquals(sportPlace.getTribuneCapacity(), sportPlaceFromDatabase.getTribuneCapacity());
    }

    private void initSportPlaceWithoutSports(SportPlace sportPlace) {
        sportPlace.setIndoor(true);
        sportPlace.setDescription("Traditional SportPlace");
        sportPlace.setChangingRoom(true);
        sportPlace.setCapacity(40);
        Manager manager = new Manager();
        manager.setId(1);
        sportPlace.setManager(manager);
        Address address = new Address();
        address.setId(1);
        sportPlace.setAddress(address);
        FloorCoverage floorCoverage = new FloorCoverage();
        floorCoverage.setId(2);
        sportPlace.setFloorCoverage(floorCoverage);
        sportPlace.setLightening(false);
        sportPlace.setOtherInfrastructureFeatures("there are balls for rent");
        sportPlace.setPricePerHour(BigDecimal.valueOf(9000.00));
        sportPlace.setSize("60x60");
        sportPlace.setShower(false);
        sportPlace.setTribuneCapacity(5000);
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
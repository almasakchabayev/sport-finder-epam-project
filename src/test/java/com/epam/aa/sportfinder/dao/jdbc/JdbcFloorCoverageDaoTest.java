package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.DaoManager;
import com.epam.aa.sportfinder.dao.FloorCoverageDao;
import com.epam.aa.sportfinder.model.FloorCoverage;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JdbcFloorCoverageDaoTest extends TestConfig {


    @Test(expected = DaoException.class)
    public void testInsertFailsIfIdNotNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            FloorCoverageDao dao = daoManager.getDao(FloorCoverage.class);
            FloorCoverage floorCoverage = new FloorCoverage();
            floorCoverage.setId(1);
            return dao.insert(floorCoverage);
        });
    }

    @Test(expected = DaoException.class)
    public void testInsertFailsIfNameAlreadyExists() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            FloorCoverageDao dao = daoManager.getDao(FloorCoverage.class);

            FloorCoverage floorCoverage = new FloorCoverage();
            floorCoverage.setName("unique");
            dao.insert(floorCoverage);

            FloorCoverage floorCoverageWithSameName = new FloorCoverage();
            floorCoverageWithSameName.setName(floorCoverage.getName());

            return dao.insert(floorCoverageWithSameName);
        });
    }

    @Test(expected = DaoException.class)
    public void testInsertFailsIfUuidAlreadyExists() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            FloorCoverageDao dao = daoManager.getDao(FloorCoverage.class);

            FloorCoverage floorCoverage = new FloorCoverage();
            dao.insert(floorCoverage);

            FloorCoverage floorCoverageWithSameUuid = new FloorCoverage();
            floorCoverageWithSameUuid.setUuid(floorCoverage.getUuid());

            return dao.insert(floorCoverageWithSameUuid);
        });
    }

    @Test
    public void testInsertSuccessWithoutIdAndUuid() throws Exception {
        DaoManager daoManager = getDaoManager();
        FloorCoverage floorCoverage = new FloorCoverage();
        daoManager.executeTx(daoManager1 -> {
            FloorCoverageDao dao = daoManager.getDao(FloorCoverage.class);
            floorCoverage.setName("sand");
            return dao.insert(floorCoverage);
        });

        Connection connection = getDataSource().getConnection();
        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT id, uuid, deleted, name " +
                "FROM FloorCoverage ORDER BY id DESC LIMIT 1");
        FloorCoverage floorCoverageFromDatabase = new FloorCoverage();

        if (resultSet.next()) {
            floorCoverageFromDatabase.setId(resultSet.getInt("id"));
            floorCoverageFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
            floorCoverageFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
            floorCoverageFromDatabase.setName(resultSet.getString("name"));
        }
        resultSet.close();
        st.close();
        connection.close();

        assertFloorCoveragesEqual(floorCoverage, floorCoverageFromDatabase);
    }

    @Test(expected = DaoException.class)
    public void testUpdateFailsIfIdIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            FloorCoverageDao dao = daoManager.getDao(FloorCoverage.class);

            FloorCoverage floorCoverage = new FloorCoverage();
            floorCoverage.setName("parket");

            dao.update(floorCoverage);
            return null;
        });
    }

    @Test
    public void testUpdateSuccessIfIdNotNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        FloorCoverage floorCoverage = new FloorCoverage();
        daoManager.executeTx(daoManager1 -> {
            FloorCoverageDao dao = daoManager.getDao(FloorCoverage.class);

            floorCoverage.setId(2);
            floorCoverage.setName("lenoleum");

            dao.update(floorCoverage);
            return null;
        });

        Connection connection = getDataSource().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, name " +
                "FROM FloorCoverage WHERE id = ?");
        pst.setInt(1, floorCoverage.getId());
        ResultSet resultSet = pst.executeQuery();
        FloorCoverage floorCoverageFromDatabase = new FloorCoverage();

        if (resultSet.next()) {
            floorCoverageFromDatabase.setId(resultSet.getInt("id"));
            floorCoverageFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
            floorCoverageFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
            floorCoverageFromDatabase.setName(resultSet.getString("name"));
        }
        resultSet.close();
        pst.close();
        connection.close();

        assertFloorCoveragesEqual(floorCoverage, floorCoverageFromDatabase);
    }

    @Test
    public void testDeleteInDbAndAssignTrueToObject() throws Exception {
        DaoManager daoManager = getDaoManager();
        FloorCoverage floorCoverage = new FloorCoverage();
        daoManager.executeTx(daoManager1 -> {
            FloorCoverageDao dao = daoManager.getDao(FloorCoverage.class);

            floorCoverage.setId(1);
            return dao.delete(floorCoverage);
        });

        Connection connection = getDataSource().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, name " +
                "FROM FloorCoverage WHERE id = ?");
        pst.setInt(1, floorCoverage.getId());
        ResultSet resultSet = pst.executeQuery();
        FloorCoverage floorCoverageFromDatabase = new FloorCoverage();

        if (resultSet.next()) {
            floorCoverageFromDatabase.setId(resultSet.getInt("id"));
            floorCoverageFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
            floorCoverageFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
            floorCoverageFromDatabase.setName(resultSet.getString("name"));
        }
        resultSet.close();
        pst.close();
        connection.close();

        assertEquals(floorCoverageFromDatabase.isDeleted(), true);
        assertEquals(floorCoverage.isDeleted(), floorCoverageFromDatabase.isDeleted());
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfIdIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            FloorCoverageDao dao = daoManager.getDao(FloorCoverage.class);

            FloorCoverage dummyfloorCoverage = new FloorCoverage();
            return dao.findById(dummyfloorCoverage.getId());
        });
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfIdIsNegative() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            FloorCoverageDao dao = daoManager.getDao(FloorCoverage.class);
            return dao.findById(-1);
        });
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfElementCouldNotBeFounded() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            FloorCoverageDao dao = daoManager.getDao(FloorCoverage.class);
            return dao.findById(100000000);
        });
    }

    @Test
    public void testFindByIdSuccessIfValidId() throws Exception {
        DaoManager daoManager = getDaoManager();
        FloorCoverage floorCoverage = daoManager.executeTx(daoManager1 -> {
            FloorCoverageDao dao = daoManager.getDao(FloorCoverage.class);
            return dao.findById(1);
        });

        Connection connection = getDataSource().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, name " +
                "FROM FloorCoverage WHERE id = ?");
        pst.setInt(1, floorCoverage.getId());
        ResultSet resultSet = pst.executeQuery();
        FloorCoverage floorCoverageFromDatabase = new FloorCoverage();

        if (resultSet.next()) {
            floorCoverageFromDatabase.setId(resultSet.getInt("id"));
            floorCoverageFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
            floorCoverageFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
            floorCoverageFromDatabase.setName(resultSet.getString("name"));}

        connection.close();

        assertFloorCoveragesEqual(floorCoverage, floorCoverageFromDatabase);
    }

    @Test
    public void testFindAllSuccess() throws Exception {
        DaoManager daoManager = getDaoManager();
        List<FloorCoverage> floorCoverages = daoManager.executeTx(daoManager1 -> {
            FloorCoverageDao dao = daoManager.getDao(FloorCoverage.class);
            return dao.findAll();
        });

        Connection connection = getDataSource().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, name " +
                "FROM FloorCoverage");
        ResultSet resultSet = pst.executeQuery();

        List<FloorCoverage> floorCoveragesFromDatabase = new ArrayList<>();
        while (resultSet.next()) {
            FloorCoverage floorCoverageFromDatabase = new FloorCoverage();
            floorCoverageFromDatabase.setId(resultSet.getInt("id"));
            floorCoverageFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
            floorCoverageFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
            floorCoverageFromDatabase.setName(resultSet.getString("name"));
            floorCoveragesFromDatabase.add(floorCoverageFromDatabase);
        }

        connection.close();

        assertEquals(floorCoverages.size(), floorCoveragesFromDatabase.size());
        for (int i = 0; i < floorCoverages.size(); i++) {
            assertFloorCoveragesEqual(floorCoverages.get(i), floorCoveragesFromDatabase.get(i));
        }
    }

    @Test
    public void testFindByNameSuccess() throws Exception {
        DaoManager daoManager = getDaoManager();
        FloorCoverage floorCoverage = daoManager.executeTx(daoManager1 -> {
            FloorCoverageDao dao = daoManager.getDao(FloorCoverage.class);
            FloorCoverage f = new FloorCoverage();
            f.setName("natural grass");
            return dao.findByName(f);
        });

        Connection connection = getDataSource().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, name " +
                "FROM FloorCoverage WHERE name = 'natural grass'");
        ResultSet resultSet = pst.executeQuery();

        FloorCoverage floorCoverageFromDatabase = new FloorCoverage();
        while (resultSet.next()) {
            floorCoverageFromDatabase.setId(resultSet.getInt("id"));
            floorCoverageFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
            floorCoverageFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
            floorCoverageFromDatabase.setName(resultSet.getString("name"));
        }

        connection.close();

        assertFloorCoveragesEqual(floorCoverage, floorCoverageFromDatabase);
    }

    public void assertFloorCoveragesEqual(FloorCoverage floorCoverage, FloorCoverage floorCoverageFromDatabase) {
        assertEquals(floorCoverage.getId(), floorCoverageFromDatabase.getId());
        assertEquals(floorCoverage.getUuid(), floorCoverageFromDatabase.getUuid());
        assertEquals(floorCoverage.isDeleted(), floorCoverageFromDatabase.isDeleted());
        assertEquals(floorCoverage.getName(), floorCoverageFromDatabase.getName());

    }
}
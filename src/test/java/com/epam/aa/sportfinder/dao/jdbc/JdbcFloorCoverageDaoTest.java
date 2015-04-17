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
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class JdbcFloorCoverageDaoTest extends GlobalTestDataSource {


    @Test(expected = DaoException.class)
    public void testInsertFailsIfIdNotNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        FloorCoverageDao dao = daoManager.getFloorCoverageDao();

        FloorCoverage floorCoverage = new FloorCoverage();
        floorCoverage.setId(1);

        dao.insert(floorCoverage);
    }

    @Test(expected = DaoException.class)
    public void testInsertFailsIfNameAlreadyExists() throws Exception {
        DaoManager daoManager = getDaoManager();
        FloorCoverageDao dao = daoManager.getFloorCoverageDao();

        FloorCoverage floorCoverage = new FloorCoverage();
        floorCoverage.setName("unique");
        dao.insert(floorCoverage);

        FloorCoverage floorCoverageWithSameName = new FloorCoverage();
        floorCoverageWithSameName.setName(floorCoverage.getName());

        dao.insert(floorCoverageWithSameName);
    }

    @Test(expected = DaoException.class)
    public void testInsertFailsIfUuidAlreadyExists() throws Exception {
        DaoManager daoManager = getDaoManager();
        FloorCoverageDao dao = daoManager.getFloorCoverageDao();

        FloorCoverage floorCoverage = new FloorCoverage();
        dao.insert(floorCoverage);

        FloorCoverage floorCoverageWithSameUuid = new FloorCoverage();
        floorCoverageWithSameUuid.setUuid(floorCoverage.getUuid());

        dao.insert(floorCoverageWithSameUuid);
    }

    @Test
    public void testInsertSuccessWithoutIdAndUuid() throws Exception {
        DaoManager daoManager = getDaoManager();
        FloorCoverageDao dao = daoManager.getFloorCoverageDao();

        Connection connection = getDataSource().getConnection();

        FloorCoverage floorCoverage = new FloorCoverage();
        floorCoverage.setName("sand");

        dao.insert(floorCoverage);

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
        FloorCoverageDao dao = daoManager.getFloorCoverageDao();

        FloorCoverage floorCoverage = new FloorCoverage();
        floorCoverage.setName("parket");

        dao.update(floorCoverage);
    }

    @Test
    public void testUpdateSuccessIfIdNotNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        FloorCoverageDao dao = daoManager.getFloorCoverageDao();

        Connection connection = getDataSource().getConnection();

        FloorCoverage floorCoverage = new FloorCoverage();
        floorCoverage.setId(2);
        floorCoverage.setName("lenoleum");

        dao.update(floorCoverage);

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
        FloorCoverageDao dao = daoManager.getFloorCoverageDao();

        Connection connection = getDataSource().getConnection();

        FloorCoverage floorCoverage = new FloorCoverage();
        floorCoverage.setId(1);
        dao.delete(floorCoverage);

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
        FloorCoverageDao dao = daoManager.getFloorCoverageDao();

        FloorCoverage dummyfloorCoverage = new FloorCoverage();
        FloorCoverage floorCoverage = dao.findById(dummyfloorCoverage.getId());
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfIdIsNegative() throws Exception {
        DaoManager daoManager = getDaoManager();
        FloorCoverageDao dao = daoManager.getFloorCoverageDao();

        FloorCoverage floorCoverage = dao.findById(-1);
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfElementCouldNotBeFounded() throws Exception {
        DaoManager daoManager = getDaoManager();
        FloorCoverageDao dao = daoManager.getFloorCoverageDao();
        FloorCoverage floorCoverage = dao.findById(100000000);
    }

    @Test
    public void testFindByIdSuccessIfValidId() throws Exception {
        DaoManager daoManager = getDaoManager();
        FloorCoverageDao dao = daoManager.getFloorCoverageDao();

        Connection connection = getDataSource().getConnection();
        FloorCoverage floorCoverage = dao.findById(1);

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

    public void assertFloorCoveragesEqual(FloorCoverage floorCoverage, FloorCoverage floorCoverageFromDatabase) {
        assertEquals(floorCoverage.getId(), floorCoverageFromDatabase.getId());
        assertEquals(floorCoverage.getUuid(), floorCoverageFromDatabase.getUuid());
        assertEquals(floorCoverage.isDeleted(), floorCoverage.isDeleted());
        assertEquals(floorCoverage.getName(), floorCoverageFromDatabase.getName());

    }
}
package com.epam.aa.sportfinder.dao.jdbc;

import com.epam.aa.sportfinder.dao.DaoException;
import com.epam.aa.sportfinder.dao.DaoManager;
import com.epam.aa.sportfinder.dao.SportDao;
import com.epam.aa.sportfinder.model.Sport;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class JdbcSportDaoTest extends TestConfig {


    @Test(expected = DaoException.class)
    public void testInsertFailsIfIdNotNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportDao dao = daoManager.getDao(Sport.class);

            Sport sport = new Sport();
            sport.setId(1);

            return dao.insert(sport);
        });
    }

    @Test(expected = DaoException.class)
    public void testInsertFailsIfNameAlreadyExists() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportDao dao = daoManager.getDao(Sport.class);

            Sport sport = new Sport();
            sport.setName("unique");
            dao.insert(sport);

            Sport sportWithSameName = new Sport();
            sportWithSameName.setName(sport.getName());

            return dao.insert(sportWithSameName);
        });
    }

    @Test(expected = DaoException.class)
    public void testInsertFailsIfUuidAlreadyExists() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportDao dao = daoManager.getDao(Sport.class);

            Sport sport = new Sport();
            dao.insert(sport);

            Sport sportWithSameUuid = new Sport();
            sportWithSameUuid.setUuid(sport.getUuid());

            return dao.insert(sportWithSameUuid);
        });
    }

    @Test
    public void testInsertSuccessWithoutIdAndUuid() throws Exception {
        DaoManager daoManager = getDaoManager();
        Sport sport = new Sport();
        daoManager.executeTx(daoManager1 -> {
            SportDao dao = daoManager.getDao(Sport.class);
            sport.setName("sand");
            return dao.insert(sport);
        });

        Connection connection = getDataSource().getConnection();
        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT id, uuid, deleted, name " +
                "FROM Sport ORDER BY id DESC LIMIT 1");
        Sport sportFromDatabase = new Sport();

        if (resultSet.next()) {
            sportFromDatabase.setId(resultSet.getInt("id"));
            sportFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
            sportFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
            sportFromDatabase.setName(resultSet.getString("name"));
        }
        resultSet.close();
        st.close();
        connection.close();

        assertSportsEqual(sport, sportFromDatabase);
    }

    @Test(expected = DaoException.class)
    public void testUpdateFailsIfIdIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportDao dao = daoManager.getDao(Sport.class);

            Sport sport = new Sport();
            sport.setName("parket");

            dao.update(sport);
            return null;
        });
    }

    @Test
    public void testUpdateSuccessIfIdNotNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        Sport sport = new Sport();
        daoManager.executeTx(daoManager1 -> {
            SportDao dao = daoManager.getDao(Sport.class);
            sport.setId(2);
            sport.setName("lenoleum");
            dao.update(sport);
            return null;
        });

        Connection connection = getDataSource().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, name " +
                "FROM Sport WHERE id = ?");
        pst.setInt(1, sport.getId());
        ResultSet resultSet = pst.executeQuery();
        Sport sportFromDatabase = new Sport();

        if (resultSet.next()) {
            sportFromDatabase.setId(resultSet.getInt("id"));
            sportFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
            sportFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
            sportFromDatabase.setName(resultSet.getString("name"));
        }
        resultSet.close();
        pst.close();
        connection.close();

        assertSportsEqual(sport, sportFromDatabase);
    }

    @Test
    public void testDeleteInDbAndAssignTrueToObject() throws Exception {
        DaoManager daoManager = getDaoManager();
        Sport sport = new Sport();
        daoManager.executeTx(daoManager1 -> {
            SportDao dao = daoManager.getDao(Sport.class);
            sport.setId(1);
            return dao.delete(sport);
        });

        Connection connection = getDataSource().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, name " +
                "FROM Sport WHERE id = ?");
        pst.setInt(1, sport.getId());
        ResultSet resultSet = pst.executeQuery();
        Sport sportFromDatabase = new Sport();

        if (resultSet.next()) {
            sportFromDatabase.setId(resultSet.getInt("id"));
            sportFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
            sportFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
            sportFromDatabase.setName(resultSet.getString("name"));
        }
        resultSet.close();
        pst.close();
        connection.close();

        assertEquals(sportFromDatabase.isDeleted(), true);
        assertEquals(sport.isDeleted(), sportFromDatabase.isDeleted());
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfIdIsNull() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportDao dao = daoManager.getDao(Sport.class);
            Sport dummysport = new Sport();
            return dao.findById(dummysport.getId());
        });
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfIdIsNegative() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportDao dao = daoManager.getDao(Sport.class);
            return dao.findById(-1);
        });
    }

    @Test(expected = DaoException.class)
    public void testFindByIdFailsIfElementCouldNotBeFounded() throws Exception {
        DaoManager daoManager = getDaoManager();
        daoManager.executeTx(daoManager1 -> {
            SportDao dao = daoManager.getDao(Sport.class);
            return dao.findById(100000000);
        });
    }

    @Test
    public void testFindByIdSuccessIfValidId() throws Exception {
        DaoManager daoManager = getDaoManager();
        Sport sport = daoManager.executeTx(daoManager1 -> {
            SportDao dao = daoManager.getDao(Sport.class);
            return dao.findById(1);
        });

        Connection connection = getDataSource().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, name " +
                "FROM Sport WHERE id = ?");
        pst.setInt(1, sport.getId());
        ResultSet resultSet = pst.executeQuery();
        Sport sportFromDatabase = new Sport();

        if (resultSet.next()) {
            sportFromDatabase.setId(resultSet.getInt("id"));
            sportFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
            sportFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
            sportFromDatabase.setName(resultSet.getString("name"));
        }
        resultSet.close();
        pst.close();
        connection.close();

        assertSportsEqual(sport, sportFromDatabase);
    }

    @Test
    public void testFindAllNonDeletedSuccess() throws Exception {
        DaoManager daoManager = getDaoManager();
        List<Sport> sports = daoManager.executeTx(daoManager1 -> {
            SportDao dao = daoManager.getDao(Sport.class);
            return dao.findAll();
        });

        Connection connection = getDataSource().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, name " +
                "FROM Sport WHERE deleted = FALSE ");
        ResultSet resultSet = pst.executeQuery();

        List<Sport> sportsFromDatabase = new ArrayList<>();
        while (resultSet.next()) {
            Sport sportFromDatabase = new Sport();
            sportFromDatabase.setId(resultSet.getInt("id"));
            sportFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
            sportFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
            sportFromDatabase.setName(resultSet.getString("name"));
            sportsFromDatabase.add(sportFromDatabase);
        }
        resultSet.close();
        pst.close();
        connection.close();

        assertEquals(sports.size(), sportsFromDatabase.size());
        for (int i = 0; i < sports.size(); i++) {
            assertSportsEqual(sports.get(i), sportsFromDatabase.get(i));
        }
    }

    @Test
    public void testFindByNameSuccess() throws Exception {
        DaoManager daoManager = getDaoManager();
        Sport sport = daoManager.executeTx(daoManager1 -> {
            SportDao dao = daoManager.getDao(Sport.class);
            Sport f = new Sport();
            f.setName("football");
            return dao.findByName(f);
        });

        Connection connection = getDataSource().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT id, uuid, deleted, name " +
                "FROM Sport WHERE name = 'football'");
        ResultSet resultSet = pst.executeQuery();

        Sport sportFromDatabase = new Sport();
        if (resultSet.next()) {
            sportFromDatabase.setId(resultSet.getInt("id"));
            sportFromDatabase.setUuid((UUID) resultSet.getObject("uuid"));
            sportFromDatabase.setDeleted(resultSet.getBoolean("deleted"));
            sportFromDatabase.setName(resultSet.getString("name"));
        }
        resultSet.close();
        pst.close();
        connection.close();

        assertSportsEqual(sport, sportFromDatabase);
    }

    public void assertSportsEqual(Sport sport, Sport sportFromDatabase) {
        assertEquals(sport.getId(), sportFromDatabase.getId());
        assertEquals(sport.getUuid(), sportFromDatabase.getUuid());
        assertEquals(sport.isDeleted(), sportFromDatabase.isDeleted());
        assertEquals(sport.getName(), sportFromDatabase.getName());

    }
}
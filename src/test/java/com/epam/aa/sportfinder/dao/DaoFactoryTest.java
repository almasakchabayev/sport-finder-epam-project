package com.epam.aa.sportfinder.dao;

import com.epam.aa.sportfinder.dao.jdbc.JdbcDaoFactory;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DaoFactoryTest {

    @Test
    public void testGetInstance() throws Exception {
        DaoFactory.setDefaultType("jdbc");
        DaoFactory daoFactory = DaoFactory.getInstance();
        assertTrue(daoFactory instanceof JdbcDaoFactory);
    }
}
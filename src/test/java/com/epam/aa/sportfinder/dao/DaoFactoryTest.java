package com.epam.aa.sportfinder.dao;

import com.epam.aa.sportfinder.dao.jdbc.JdbcDaoFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class DaoFactoryTest {

    @Test
    public void testGetInstance() throws Exception {
        assert (DaoFactory.getInstance() instanceof JdbcDaoFactory);
    }

    @Test
    public void testCreateDaoManager() throws Exception {

    }
}
package com.epam.aa.sportfinder.config;

import com.epam.aa.sportfinder.dao.DaoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(AppContextListener.class);

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        initDaoFactory();
    }

    private void initDaoFactory() {
        String daoProperty = AppProperties.getDaoProperty("dao.factory");
        DaoFactory.setImpl(daoProperty);
        logger.info("DaoFactory is successfully initialized with implementation {}", daoProperty);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //TODO: how to close datasource here?
    }
}
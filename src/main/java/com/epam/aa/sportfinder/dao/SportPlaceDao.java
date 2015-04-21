package com.epam.aa.sportfinder.dao;

import com.epam.aa.sportfinder.model.Sport;
import com.epam.aa.sportfinder.model.SportPlace;

public interface SportPlaceDao extends GenericDao<SportPlace> {
    SportPlace insertCorrespondingSports(SportPlace sportPlace) throws DaoException;
    SportPlace removeSportFromCorrespondingSports(SportPlace sportPlace, Sport sport) throws DaoException;
    SportPlace addSportToCorrespondingSports(SportPlace sportPlace, Sport sport) throws DaoException;
}

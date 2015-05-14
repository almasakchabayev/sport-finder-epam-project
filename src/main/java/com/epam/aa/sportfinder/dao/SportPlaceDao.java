package com.epam.aa.sportfinder.dao;

import com.epam.aa.sportfinder.model.Manager;
import com.epam.aa.sportfinder.model.Sport;
import com.epam.aa.sportfinder.model.SportPlace;

import java.util.List;

public interface SportPlaceDao extends GenericDao<SportPlace> {
    SportPlace insertCorrespondingSports(SportPlace sportPlace) throws DaoException;
    SportPlace removeSportFromCorrespondingSports(SportPlace sportPlace, Sport sport) throws DaoException;
    SportPlace addSportToCorrespondingSports(SportPlace sportPlace, Sport sport) throws DaoException;
    List<Integer> findCorrespondingSportIds(SportPlace sportPlace) throws DaoException;

    List<SportPlace> findByManager(Manager manager) throws DaoException;

    void deleteCorrespondingSports(SportPlace sportPlace);

    SportPlace insertCorrespondingImages(SportPlace sportPlace) throws DaoException;

    List<Integer> findCorrespondingImageIds(SportPlace sportPlace);

    void deleteCorrespondingImages(SportPlace sportPlace);

    List<SportPlace> findDeletedByManager(Manager manager);

    void undelete(SportPlace sportPlace);

    SportPlace findDeletedOrNonDeletedById(Integer id);

    List<SportPlace> findAll();
}

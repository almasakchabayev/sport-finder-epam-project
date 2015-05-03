package com.epam.aa.sportfinder.dao;

import com.epam.aa.sportfinder.model.Sport;

import java.util.List;

public interface SportDao extends GenericDao<Sport> {
    List<Sport> findAll();
    //TODO:
//    List<Sport> findSportsByIds(List<Integer>) throws DaoException;
}

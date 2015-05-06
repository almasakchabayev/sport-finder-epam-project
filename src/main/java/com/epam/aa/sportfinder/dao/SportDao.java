package com.epam.aa.sportfinder.dao;

import com.epam.aa.sportfinder.model.Sport;

import java.util.List;

public interface SportDao extends GenericDao<Sport> {
    List<Sport> findAll();

    Sport findByName(Sport sport);
    //TODO:
//    List<Sport> findSportsByIds(List<Integer>) throws DaoException;
}

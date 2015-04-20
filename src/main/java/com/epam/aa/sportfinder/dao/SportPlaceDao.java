package com.epam.aa.sportfinder.dao;

import com.epam.aa.sportfinder.model.SportPlace;

public interface SportPlaceDao extends GenericDao<SportPlace> {
    SportPlace insertWithSports(SportPlace sportPlace);
}

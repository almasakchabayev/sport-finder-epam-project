package com.epam.aa.sportfinder.dao;

import com.epam.aa.sportfinder.model.FloorCoverage;

import java.util.List;

public interface FloorCoverageDao extends GenericDao<FloorCoverage> {

    List<FloorCoverage> findAll();
}

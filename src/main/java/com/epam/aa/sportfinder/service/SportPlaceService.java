package com.epam.aa.sportfinder.service;

import com.epam.aa.sportfinder.dao.*;
import com.epam.aa.sportfinder.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SportPlaceService extends BaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SportPlaceService.class);

    public static SportPlace create(SportPlace sportPlace) {
        DaoManager daoManager = createDaoManager();
        return daoManager.executeTx(m -> {
            SportPlaceDao sportPlaceDao = m.getDao(SportPlace.class);
            AddressDao addressDao = m.getDao(Address.class);
            ImageDao imageDao = m.getDao(Image.class);
            // no need for manager as manager was extracted previously
            SportPlace result = null;

            try {
                Address address = addressDao.insert(sportPlace.getAddress());
                sportPlace.setAddress(address);

                result = sportPlaceDao.insert(sportPlace);

                if (sportPlace.getImages().size() > 0) {
                    sportPlace.getImages().forEach(imageDao::insert);
                    sportPlaceDao.insertCorrespondingImages(result);
                }
                result = sportPlaceDao.insertCorrespondingSports(result);
                return result;
            } catch (DaoException e) {
                throw new ServiceException("error during sport place creation", e);
            }
        });
    }

    public static SportPlace findById(Integer id) {
        DaoManager daoManager = createDaoManager();

        return daoManager.executeTx(manager -> {
            SportPlaceDao sportPlaceDao = manager.getDao(SportPlace.class);
            SportPlace sportPlace = sportPlaceDao.findById(id);

            if (sportPlace != null) {
                retrieveRelatedEntities(manager, sportPlaceDao, sportPlace);
            }
            return sportPlace;
        });
    }

    public static List<SportPlace> findByManager(Manager manager) {
        DaoManager daoManager = createDaoManager();

        return daoManager.executeTx(m -> {
            SportPlaceDao sportPlaceDao = m.getDao(SportPlace.class);
            List<SportPlace> sportPlaces = sportPlaceDao.findByManager(manager);

            for (SportPlace sportPlace : sportPlaces) {
                retrieveRelatedEntities(m, sportPlaceDao, sportPlace);
            }
            return sportPlaces;
        });
    }

    public static void update(SportPlace sportPlace) {
        DaoManager daoManager = createDaoManager();
        daoManager.executeTx(manager -> {
            SportPlaceDao sportPlaceDao = manager.getDao(SportPlace.class);
            ImageDao imageDao = manager.getDao(Image.class);
            AddressDao addressDao = manager.getDao(Address.class);
            try {
                addressDao.update(sportPlace.getAddress());
                sportPlaceDao.update(sportPlace);
                sportPlaceDao.deleteCorrespondingSports(sportPlace);
                sportPlaceDao.insertCorrespondingSports(sportPlace);
                sportPlaceDao.deleteCorrespondingImages(sportPlace);
                for (Image image : sportPlace.getImages()) {
                    if (image.getId() != null)
                        continue;

                    image = imageDao.insert(image);
                }
                sportPlaceDao.insertCorrespondingImages(sportPlace);
                return null;
            } catch (DaoException e) {
                throw new ServiceException("error during sport place update", e);
            }
        });
    }

    private static void retrieveRelatedEntities(DaoManager manager, SportPlaceDao sportPlaceDao, SportPlace sportPlace) {
        FloorCoverageDao floorCoverageDao = manager.getDao(FloorCoverage.class);
        FloorCoverage floorCoverage = floorCoverageDao.findById(sportPlace.getFloorCoverage().getId());
        sportPlace.setFloorCoverage(floorCoverage);

        AddressDao addressDao = manager.getDao(Address.class);
        Address address = addressDao.findById(sportPlace.getAddress().getId());
        sportPlace.setAddress(address);

        List<Integer> correspondingSportIds = sportPlaceDao.findCorrespondingSportIds(sportPlace);
        SportDao sportDao = manager.getDao(Sport.class);
        for (Integer correspondingSportId : correspondingSportIds) {
            Sport sport = sportDao.findById(correspondingSportId);
            sportPlace.addSport(sport);
        }

        List<Integer> imageIds = sportPlaceDao.findCorrespondingImageIds(sportPlace);
        ImageDao imageDao = manager.getDao(Image.class);
        for (Integer imageId : imageIds) {
            Image image = imageDao.findById(imageId);
            sportPlace.addImage(image);
        }
    }

    public static void delete(Integer id) {
        DaoManager daoManager = createDaoManager();
        daoManager.executeTx(manager -> {
            SportPlaceDao sportPlaceDao = manager.getDao(SportPlace.class);
            try {
                SportPlace sportPlace = new SportPlace();
                sportPlace.setId(id);
                sportPlaceDao.delete(sportPlace);
                return null;
            } catch (DaoException e) {
                throw new ServiceException("error during sport place update", e);
            }
        });
    }

    public static List<SportPlace> findDeletedByManager(Manager manager) {
        DaoManager daoManager = createDaoManager();

        return daoManager.executeTx(m -> {
            SportPlaceDao sportPlaceDao = m.getDao(SportPlace.class);
            List<SportPlace> sportPlaces = sportPlaceDao.findDeletedByManager(manager);

            for (SportPlace sportPlace : sportPlaces) {
                retrieveRelatedEntities(m, sportPlaceDao, sportPlace);
            }
            return sportPlaces;
        });
    }

    public static void undelete(Integer id) {
        DaoManager daoManager = createDaoManager();
        daoManager.executeTx(manager -> {
            SportPlaceDao sportPlaceDao = manager.getDao(SportPlace.class);
            try {
                SportPlace sportPlace = new SportPlace();
                sportPlace.setId(id);
                sportPlaceDao.undelete(sportPlace);
                return null;
            } catch (DaoException e) {
                throw new ServiceException("error during sport place update", e);
            }
        });
    }

    public static SportPlace findDeletedOrNonDeletedById(Integer id) {
        DaoManager daoManager = createDaoManager();

        return daoManager.executeTx(manager -> {
            SportPlaceDao sportPlaceDao = manager.getDao(SportPlace.class);
            SportPlace sportPlace = sportPlaceDao.findDeletedOrNonDeletedById(id);

            if (sportPlace != null) {
                retrieveRelatedEntities(manager, sportPlaceDao, sportPlace);
            }
            return sportPlace;
        });
    }
}

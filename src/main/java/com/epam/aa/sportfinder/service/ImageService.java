package com.epam.aa.sportfinder.service;

import com.epam.aa.sportfinder.dao.DaoManager;
import com.epam.aa.sportfinder.dao.GenericDao;
import com.epam.aa.sportfinder.dao.ImageDao;
import com.epam.aa.sportfinder.model.Image;
import com.epam.aa.sportfinder.model.SportPlace;

import java.util.List;

public class ImageService extends BaseService {

    public static Image findById(Integer id) {
        DaoManager daoManager = createDaoManager();

        return daoManager.executeTx(m -> {
            ImageDao imageDao = m.getDao(Image.class);
            return imageDao.findById(id);
        });
    }
}

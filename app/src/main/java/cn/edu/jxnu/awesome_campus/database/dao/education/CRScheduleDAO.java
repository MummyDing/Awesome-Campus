package cn.edu.jxnu.awesome_campus.database.dao.education;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.model.education.CRScheduleModel;

/**
 * Created by KevinWu on 16-5-10.
 */
public class CRScheduleDAO implements DAO<CRScheduleModel>{
    private static final String TAG="CRScheduleDAO";


    @Override
    public boolean cacheAll(List<CRScheduleModel> list) {
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache() {

    }

    @Override
    public void loadFromNet() {

    }
}

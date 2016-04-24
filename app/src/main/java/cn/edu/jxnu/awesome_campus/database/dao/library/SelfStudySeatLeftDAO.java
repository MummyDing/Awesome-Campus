package cn.edu.jxnu.awesome_campus.database.dao.library;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.model.library.SelfStudySeatLeftModel;

/**
 * Created by KevinWu on 16-4-24.
 */
public class SelfStudySeatLeftDAO implements DAO<SelfStudySeatLeftModel> {
    @Override
    public boolean cacheAll(List<SelfStudySeatLeftModel> list) {
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

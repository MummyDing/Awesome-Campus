package cn.edu.jxnu.awesome_campus.database.dao.home;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.model.home.CampusNewsModel;

/**
 * Created by MummyDing on 16-2-2.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CampusNewsDAO implements DAO<CampusNewsModel> {
    @Override
    public boolean cacheAll(List<CampusNewsModel> list) {
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache(List<CampusNewsModel> list) {

    }

    @Override
    public void loadFromNet(List<CampusNewsModel> list) {

    }

}

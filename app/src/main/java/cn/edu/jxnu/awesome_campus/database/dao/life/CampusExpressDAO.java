package cn.edu.jxnu.awesome_campus.database.dao.life;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.model.life.CampusExpressModel;

/**
 * Created by MummyDing on 16-2-20.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CampusExpressDAO implements DAO<CampusExpressModel> {

    public static final String TAG = "CampusExpressDAO";

    @Override
    public boolean cacheAll(List<CampusExpressModel> list) {
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

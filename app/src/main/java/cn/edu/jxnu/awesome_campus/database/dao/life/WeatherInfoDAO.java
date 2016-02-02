package cn.edu.jxnu.awesome_campus.database.dao.life;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.model.life.WeatherInfoModel;

/**
 * Created by MummyDing on 16-2-2.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class WeatherInfoDAO implements DAO<WeatherInfoModel> {
    @Override
    public boolean cacheAll(List<WeatherInfoModel> list) {
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache(List<WeatherInfoModel> list) {

    }

    @Override
    public void loadFromNet(List<WeatherInfoModel> list) {

    }
}

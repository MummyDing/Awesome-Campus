package cn.edu.jxnu.awesome_campus.database.dao.home;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.model.home.CourseInfoModel;

/**
 * Created by MummyDing on 16-2-3.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseInfoDAO implements DAO<CourseInfoModel> {
    @Override
    public boolean cacheAll(List<CourseInfoModel> list) {
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache(List<CourseInfoModel> list) {

    }

    @Override
    public void loadFromNet(List<CourseInfoModel> list) {

    }
}

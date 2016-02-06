package cn.edu.jxnu.awesome_campus.database.dao.education;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.model.education.CourseScoreModel;

/**
 * Created by MummyDing on 16-2-2.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseScoreDAO implements DAO<CourseScoreModel> {
    @Override
    public boolean cacheAll(List<CourseScoreModel> list) {
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache(List<CourseScoreModel> list) {

    }

    @Override
    public void loadFromNet(List<CourseScoreModel> list) {


    }

}

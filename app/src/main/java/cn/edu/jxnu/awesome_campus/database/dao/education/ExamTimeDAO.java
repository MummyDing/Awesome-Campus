package cn.edu.jxnu.awesome_campus.database.dao.education;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.model.education.ExamTimeModel;

/**
 * Created by MummyDing on 16-2-2.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class ExamTimeDAO implements DAO<ExamTimeModel> {
    @Override
    public boolean cacheAll(List<ExamTimeModel> list) {
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

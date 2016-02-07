package cn.edu.jxnu.awesome_campus.database.dao.library;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.model.library.BorrowHistoryModel;

/**
 * Created by MummyDing on 16-2-2.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class BorrowHistoryDAO implements DAO<BorrowHistoryModel> {
    @Override
    public boolean cacheAll(List<BorrowHistoryModel> list) {
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

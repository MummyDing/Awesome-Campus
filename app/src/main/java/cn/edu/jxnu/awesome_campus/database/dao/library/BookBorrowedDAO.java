package cn.edu.jxnu.awesome_campus.database.dao.library;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.model.library.BookBorrowedModel;

/**
 * Created by MummyDing on 16-2-2.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class BookBorrowedDAO implements DAO<BookBorrowedModel> {
    @Override
    public boolean cacheAll(List<BookBorrowedModel> list) {
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
